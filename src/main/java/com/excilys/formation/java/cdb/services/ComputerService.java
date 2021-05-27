package com.excilys.formation.java.cdb.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.java.cdb.models.Computer;
import com.excilys.formation.java.cdb.persistence.daos.ComputerDAO;
import com.excilys.formation.java.cdb.validator.ComputerValidator;

/**
 * Represents a computer service.
 * @author Laetitia Tureau
 */
public class ComputerService {

    /**
     * A DAO instance used to encapsulate the logic for retrieving, saving and updating table computer data into the database.
     */
    private static ComputerDAO computerInstance = ComputerDAO.getInstance();

    /** Class logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ComputerService.class);

    private ComputerValidator computerValidator;

    /**
     * Creates a service to computer operations.
     */
    public ComputerService() {
        this.computerValidator = ComputerValidator.getInstance();
    }

    /**
     * Retrieve all the computers in the database.
     * @return a list of computers
     */
    public List<Computer> listAll() {
        return computerInstance.getAllComputers();
    }

    /**
     * Create a computer.
     * @param name A String containing the computer's name
     * @return the number of rows inserted
     */
    public int createComputer(String name) {
        return computerInstance.createComputer(name);
    }

    /**
     * Retrieve a computer with a specific id.
     * @param id the computer's id
     * @return a computer or throw exception
     */
    public Computer findById(Long id) {
        Optional<Computer> opt = computerInstance.findById(id);
        // TODO : create proper exception
        return opt.orElseThrow(RuntimeException::new);
    }

    /**
     * Delete a computer.
     * @param id A Long containing the computer's id
     * @return the number of rows deleted
     */
    public int deleteComputer(Long id) {
        return computerInstance.deleteComputer(id);
    }

    /**
     * Update a computer's name.
     * @param name A String containing the computer's name
     * @param id A Long containing the computer's id
     * @return the number of rows updated
     */
    public int updateName(String name, Long id) {
        return computerInstance.updateName(name, id);
    }

    /**
     * Update a computer's introduced date.
     * @param date A Timestamp as the computer's introduced date
     * @param id A Long containing the computer's id
     * @return a computer
     */
    public Computer updateIntroduced(Timestamp date, Long id) {
        Computer computer = null;
        try {
            computer = findById(id);
            // TODO: only pass computer built with date and fail to update it if forbidden
            computerValidator.validateComputerDate(computer, date, true);
            computerInstance.updateIntroduced(date, id);
        } catch (Exception exe) {
            // TODO: use proper exception
            LOGGER.error("Erreur lors de l'exécution de la requête", exe);
        }
        return computer;
    }

    /**
     * Update a computer's discontinued date.
     * @param date A Timestamp as the computer's discontinued date
     * @param id A Long containing the computer's id
     * @return a computer
     */
    public Computer updateDiscontinued(Timestamp date, Long id) {
        Computer computer = null;
        try {
            computer = findById(id);
            // TODO: only pass computer built with date and fail to update it if forbidden
            computerValidator.validateComputerDate(computer, date, false);
            computerInstance.updateDiscontinued(date, id);
        } catch (Exception exe) {
            // TODO: use proper exception
            LOGGER.error("Erreur lors de l'exécution de la requête", exe);
        }
        return computer;
    }

    /**
     * Update a computer's manufacturer.
     * @param companyID A Long containing the computer's manufacturer id
     * @param id A Long containing the computer's id
     * @return the number of rows updated
     */
    public int updateManufacturer(Long companyID, Long id) {
        return computerInstance.updateManufacturer(companyID, id);
    }
}
