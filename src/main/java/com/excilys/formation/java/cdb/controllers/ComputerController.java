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
}
