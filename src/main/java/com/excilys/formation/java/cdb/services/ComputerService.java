package com.excilys.formation.java.cdb.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;

import com.excilys.formation.java.cdb.exceptions.MyPersistenceException;
import com.excilys.formation.java.cdb.models.Computer;
import com.excilys.formation.java.cdb.persistence.daos.CompanyDAO;
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
    private static final Logger LOGGER = Logger.getLogger(ComputerService.class);

    private ComputerValidator computerValidator = ComputerValidator.getInstance();
    private static CompanyService companyService = new CompanyService();
    private static Computer computerToUpdate;

    /**
     * Initialize ComputerDAO instance.
     * @param computerInstance the instance
     */
    public void setComputerInstance(ComputerDAO computerInstance) {
        ComputerService.computerInstance = computerInstance;
    }

    /**
     * Initialize CompanyDAO instance.
     * @param companyInstance the instance
     */
    public void setCompanyInstance(CompanyDAO companyInstance) {
        ComputerService.companyService.setCompanyInstance(companyInstance);
    }

    /**
     * Retrieve all the computers in the database.
     * @return a list of computers
     */
    public List<Computer> getComputers() {
        return computerInstance.getAllComputers();
    }

    /**
     * Retrieve all the computers in the database.
     * @param page current page
     * @return a list of computers
     */
    public List<Computer> getPaginatedComputers(Pagination page) {
        return computerInstance.getPaginatedComputers(page);
    }

    /**
     * Create a computer.
     * @param computer the computer to create
     * @return the computer saved in database
     */
    public Computer createComputer(Computer computer) {
        computerValidator.validateComputerDate(computer);
        if (computer.getManufacturer() != null) {
            if (computer.getManufacturer().getId() != 0) {
                companyService.findById(computer.getManufacturer().getId());
            } else {
                throw new MyPersistenceException("company does not exist in database");
            }
        }
        return computerInstance.createComputer(computer);
    }

    /**
     * Retrieve a computer with a specific id.
     * @param id the computer's id
     * @return a computer or throw exception
     */
    public Computer findById(Long id) {
        Optional<Computer> opt = computerInstance.findById(id);
        return opt.orElseThrow(MyPersistenceException::new);
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
     * Update a computer.
     * @param computer to update
     * @return the updated computer
     */
    public Computer update(Computer computer) {
        Long id = computer.getId();
        computerToUpdate = findById(id);
        if (computer.getName() != null) {
            updateName(computer.getName(), id);
        }
        if (computer.getIntroduced() != null) {
            updateIntroduced(Timestamp.valueOf(computer.getIntroduced().atStartOfDay()), id);
        }
        if (computer.getDiscontinued() != null) {
            updateDiscontinued(Timestamp.valueOf(computer.getDiscontinued().atStartOfDay()), id);
        }
        if (computer.getManufacturer() != null) {
            updateManufacturer(computer.getManufacturer().getId(), id);
        }
        return computer;
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
    public int updateIntroduced(Timestamp date, Long id) {
        computerValidator.validateComputerDate(computerToUpdate);
        return computerInstance.updateIntroduced(date, id);
    }

    /**
     * Update a computer's discontinued date.
     * @param date A Timestamp as the computer's discontinued date
     * @param id A Long containing the computer's id
     * @return a computer
     */
    public int updateDiscontinued(Timestamp date, Long id) {
        computerValidator.validateComputerDate(computerToUpdate);
        return computerInstance.updateDiscontinued(date, id);
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
