package com.excilys.formation.java.cdb.services;

import java.util.List;
import java.util.Optional;

import com.excilys.formation.java.cdb.models.SearchCriteria;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.formation.java.cdb.models.Computer;
import com.excilys.formation.java.cdb.persistence.daos.ComputerDAO;
import com.excilys.formation.java.cdb.persistence.daos.ComputerRepository;
import com.excilys.formation.java.cdb.servlets.ListComputerServlet;

/**
 * Represents a computer service.
 * @author Laetitia Tureau
 */
@Service
public class ComputerService {

    @Autowired
    private ComputerRepository computerRepository;

    @Autowired
    private ComputerDAO computerDAO;

    private static final Logger LOGGER = Logger.getLogger(ListComputerServlet.class);

    /**
     * Creates a computer service.
     * @param computerRepository jpa repository
     * @param computerInstance jdbc dao
     */
    public ComputerService(ComputerRepository computerRepository, ComputerDAO computerInstance) {
        super();
        this.computerRepository = computerRepository;
        this.computerDAO = computerInstance;
    }

    /**
     * Retrieve all the computers in the database.
     * @return a list of computers
     */
    public List<Computer> getComputers() {
        return this.computerRepository.findAll();
    }

    /**
     * Find all computers matching given criteria.
     * @param criteria represents the search criteria
     * @return a list of computers matching the criteria
     */
    public List<Computer> findByCriteria(SearchCriteria criteria) {
       return this.computerDAO.findByCriteria(criteria);
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
}
