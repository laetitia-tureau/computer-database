package com.excilys.formation.java.cdb.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.excilys.formation.java.cdb.dtos.ComputerDTO;
import com.excilys.formation.java.cdb.exceptions.MyPersistenceException;
import com.excilys.formation.java.cdb.mappers.ComputerMapper;
import com.excilys.formation.java.cdb.models.Computer;
import com.excilys.formation.java.cdb.services.ComputerService;
import com.excilys.formation.java.cdb.services.Pagination;
import com.excilys.formation.java.cdb.services.SearchCriteria;
import com.excilys.formation.java.cdb.validator.ComputerValidator;

public class ComputerController {
    private static ComputerService computerService = new ComputerService();

    /**
     * Retrieve computers encapsulated in a dto.
     * @param criteria
     * @param
     * @return list of computers dto
     */
    public static List<ComputerDTO> getComputersPerPage(Pagination page, SearchCriteria criteria) {
        Pagination pagination = ComputerController.getPage(String.valueOf(page.getCurrentPage()),
                String.valueOf(page.getItemsPerPage()), criteria.getItemName());
        criteria.setLimit(
                pagination.getItemsPerPage() * (pagination.getCurrentPage() - 1) + "," + pagination.getItemsPerPage());
        List<Computer> computerList = computerService.findByCriteria(criteria);

        return computerList.stream().map(c -> {
            return ComputerMapper.mapFromModelToDTO(c);
        }).collect(Collectors.toList());
    }

    /**
     * Retrieve a Pagination.
     * @param
     * @param
     * @param
     * @return a pagination service
     */
    public static Pagination getPage(String indexCurrentPage, String itemsPerPage, String search) {
        Pagination page = new Pagination(0);
        if (StringUtils.isBlank(search)) {
            page.setTotalItems(computerService.getComputers().size());
        } else {
            SearchCriteria criteria = new SearchCriteria();
            criteria.setItemName(search);
            page.setTotalItems(computerService.findByCriteria(criteria).size());
        }

        if (indexCurrentPage != null && StringUtils.isNumeric(indexCurrentPage)) {
            page.setCurrentPage(Integer.parseInt(indexCurrentPage));
        }
        if (itemsPerPage != null && StringUtils.isNumeric(itemsPerPage)) {
            page.setItemsPerPage(Integer.parseInt(itemsPerPage));
        }
        return page;
    }

    public static List<Integer> getMaxItemsPerPage(Pagination page) {
        int totalItem = page.getTotalItems();
        int pageLimitMin = new Pagination(Pagination.PageLimit.MIN.getLimit(), 0, totalItem).getTotalOfPages();
        int pageLimitMid = new Pagination(Pagination.PageLimit.MID.getLimit(), 0, totalItem).getTotalOfPages();
        int pageLimitMax = new Pagination(Pagination.PageLimit.MAX.getLimit(), 0, totalItem).getTotalOfPages();
        return Arrays.asList(pageLimitMin, pageLimitMid, pageLimitMax);
    }

    /**
     * Create a computer with his attributes.
     * @param name computer's name
     * @param introduced computer's introduced date
     * @param discontinued computer's discontinued date
     * @param companyId computer's manufacturer
     * @throws MyPersistenceException if computer is not a valid computer
     */
    public static void createComputer(String name, String introduced, String discontinued, String companyId)
            throws MyPersistenceException {
        ComputerDTO computerDTO = new ComputerDTO.ComputerBuilderDTO().name(name).introduced(introduced)
                .discontinued(discontinued).manufacturer(companyId).build();
        ComputerValidator.validateComputerDTO(computerDTO);
        Computer computer = ComputerMapper.mapFromDTOtoModel(computerDTO);
        new ComputerService().createComputer(computer);
    }

    public static Optional<ComputerDTO> findComputer(String computerId) {
        if (computerId != null && StringUtils.isNumeric(computerId)) {
            Computer computer = computerService.findById(Long.parseLong(computerId));
            return Optional.of(ComputerMapper.mapFromModelToDTO(computer));
        }
        return Optional.empty();
    }

    public static void updateComputer(String computerId, String computerName, String introduced, String discontinued,
            String companyId) {
        Optional<ComputerDTO> opt = findComputer(computerId);
        ComputerDTO computerDTO = opt.orElseThrow(MyPersistenceException::new);
        computerDTO = new ComputerDTO.ComputerBuilderDTO().id(computerId).name(computerName).introduced(introduced)
                .discontinued(discontinued).manufacturer(companyId).build();
        ComputerValidator.validateComputerDTO(computerDTO);
        Computer computer = ComputerMapper.mapFromDTOtoModel(computerDTO);
        new ComputerService().update(computer);

    }

    public static void deleteComputer(String computerId) {
        Arrays.asList(computerId.split(",")).stream().forEach(id -> computerService.deleteComputer(Long.valueOf(id)));
    }

    public static String setUrl(String search, String order, String sort) {
        String url = "?";
        if (StringUtils.isNotBlank(search)) {
            url += "search=" + search;
        }
        if (StringUtils.isNotBlank(sort)) {
            if (url.length() > 1) {
                url += "&";
            }
            url += "sort=" + sort;
        }
        if (StringUtils.isNotBlank(order)) {
            if (url.length() > 1) {
                url += "&";
            }
            url += "order=" + order;
        }
        return url;
    }
}
