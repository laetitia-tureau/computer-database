package com.excilys.formation.java.cdb.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.formation.java.cdb.models.SearchCriteria;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.excilys.formation.java.cdb.models.Computer;
import com.excilys.formation.java.cdb.models.Pagination;
import com.excilys.formation.java.cdb.daos.ComputerRepository;

/**
 * Represents a computer service.
 * @author Laetitia Tureau
 */
@Service
public class ComputerService {

    @Autowired
    private ComputerRepository computerRepository;

    private static final Logger LOGGER = Logger.getLogger(ComputerService.class);

    /**
     * Creates a computer service.
     * @param computerRepository jpa repository
     */
    public ComputerService(ComputerRepository computerRepository) {
        super();
        this.computerRepository = computerRepository;
    }

    /**
     * Retrieve all the computers in the database.
     * @return a list of computers
     */
    public List<Computer> getComputers() {
        return this.computerRepository.findAll();
    }

    /**
     * Retrieve a computer with a specific id.
     * @param id the computer's id
     * @return a computer or throw exception
     */
    public Optional<Computer> findById(Long id) {
        return this.computerRepository.findById(id);
    }

    /**
     * Delete a computer.
     * @param id A Long containing the computer's id
     */
    public void deleteComputer(Long id) {
        this.computerRepository.deleteById(id);
    }

    /**
     * Edit a computer.
     * @param computer to update
     * @return the updated computer
     */
    public Computer save(Computer computer) {
         return this.computerRepository.save(computer);
    }

    /**
     * Counts all computers by name matching the filter.
     * @param filter to compare with
     * @return numbers of computers
     */
    public int filterAndCount(String filter) {
        return this.computerRepository.countByNameContaining(filter);
    }

    /**
     * Find all computers matching given criteria.
     * @param criteria represents the search criteria
     * @param page represents pagination
     * @return a list of computers matching the criteria
     */
    public List<Computer> findByCriteria(SearchCriteria criteria, Pagination page) {
        List<Computer> computers = new ArrayList<>();
        boolean byName = StringUtils.isBlank(criteria.getItemName()) ? false : true;
        Pageable pageable = PageRequest.of(page.getCurrentPage() - 1, page.getItemsPerPage(), criteria.getOrder(), criteria.getSort().getRequest());
        Page<Computer> p;
        if (!byName) {
            p = this.computerRepository.findAll(pageable);
        } else {
            p = this.computerRepository.findAllByNameContaining(criteria.getItemName(), pageable);
        }
        p.forEach(c -> computers.add(c));
        return computers;
    }
}
