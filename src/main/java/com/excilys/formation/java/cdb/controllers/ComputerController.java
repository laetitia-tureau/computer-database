package com.excilys.formation.java.cdb.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.excilys.formation.java.cdb.dtos.ComputerDTO;
import com.excilys.formation.java.cdb.exceptions.MyPersistenceException;
import com.excilys.formation.java.cdb.mappers.ComputerMapper;
import com.excilys.formation.java.cdb.models.Computer;
import com.excilys.formation.java.cdb.services.ComputerService;
import com.excilys.formation.java.cdb.services.Pagination;
import com.excilys.formation.java.cdb.validator.ComputerValidator;

public class ComputerController {
    private static ComputerService computerService = new ComputerService();

    /**
     * Retrieve computers encapsulated in a dto.
     * @param page current page
     * @return list of computers dto
     */
    public static List<ComputerDTO> getComputersPerPage(Pagination page) {
        ComputerService computerService = new ComputerService();
        List<Computer> computerList = computerService.getPaginatedComputers(page);

        return computerList.stream().map(c -> {
            return ComputerMapper.mapFromModelToDTO(c);
        }).collect(Collectors.toList());
    }

    /**
     * Retrieve a Pagination.
     * @param pageNumber number of current page
     * @param perPage number of items per page
     * @return a pagination service
     */
    public static Pagination getPage(String pageNumber, String perPage) {
        Pagination page = new Pagination(0);
        page.setTotalItems(computerService.getComputers().size());
        if (pageNumber != null && StringUtils.isNumeric(pageNumber)) {
            page.setPage(Integer.parseInt(pageNumber));
        }
        if (perPage != null && StringUtils.isNumeric(perPage)) {
            page.setLimit(Integer.parseInt(perPage));
        }
        return page;
    }

    public static void createComputer(String name, String introduced, String discontinued, String companyId)
            throws MyPersistenceException {
        ComputerDTO computerDTO = new ComputerDTO.ComputerBuilderDTO().name(name).introduced(introduced)
                .discontinued(discontinued).manufacturer(companyId).build();
        ComputerValidator.validateComputerDTO(computerDTO);
        Computer computer = ComputerMapper.mapFromDTOtoModel(computerDTO);
        new ComputerService().createComputer(computer);
    }

    /**
     * Retrieve index for pagination.
     * @param limitMin minimum numbers of items per page
     * @param limitMid mid numbers of items per page
     * @param limitMax maximum numbers of items per page
     * @return indexes
     */
    public static List<Integer> getMaxPagePerLimit(int limitMin, int limitMid, int limitMax) {
        int totalItem = computerService.getComputers().size();
        int pageLimitMin = new Pagination(limitMin, 0, totalItem).getTotalPage();
        int pageLimitMid = new Pagination(limitMid, 0, totalItem).getTotalPage();
        int pageLimitMax = new Pagination(limitMax, 0, totalItem).getTotalPage();
        return Arrays.asList(pageLimitMin, pageLimitMid, pageLimitMax);
    }
}
