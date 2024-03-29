package com.excilys.formation.java.cdb.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.formation.java.cdb.models.Computer;
import com.excilys.formation.java.cdb.persistence.daos.CompanyDAO;
import com.excilys.formation.java.cdb.persistence.daos.ComputerDAO;

/**
 * Represents a computer service.
 * @author Laetitia Tureau
 */
@Service
public class ComputerService {

    /**
     * A DAO instance used to encapsulate the logic for retrieving, saving and updating table computer data into the database.
     */
    @Autowired
    private ComputerDAO computerInstance;

    @Autowired
    private CompanyService companyService;

    /**
     * Initialize ComputerDAO instance.
     * @param computerInstance the instance
     */
    public void setComputerInstance(ComputerDAO computerInstance) {
        this.computerInstance = computerInstance;
    }

    /**
     * Initialize CompanyDAO instance.
     * @param companyInstance the instance
     */
    public void setCompanyDAOInstance(CompanyDAO companyInstance) {
        this.companyService.setCompanyInstance(companyInstance);

    }

    /**
     * Initialize CompanyService instance.
     * @param companyInstance the instance
     */
    public void setCompanyServiceInstance(CompanyService companyInstance) {
        this.companyService = companyInstance;
    }

    /**
     * Retrieve all the computers in the database.
     * @return a list of computers
     */
    public List<Computer> getComputers() {
        return computerInstance.findAll();
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
     * Find all computers matching given criteria.
     * @param criteria represents the search criteria
     * @return a list of computers matching the criteria
     */
    public List<Computer> findByCriteria(SearchCriteria criteria) {
        return computerInstance.findByCriteria(criteria);
    }

    /**
     * Retrieve a computer with a specific id.
     * @param id the computer's id
     * @return a computer or throw exception
     */
    public Computer findById(Long id) {
        Optional<Computer> opt = computerInstance.findById(id);
        return opt.orElse(null);
    }

    /**
     * Create a computer.
     * @param computer the computer to create
     * @return the computer saved in database
     */
    public Computer createComputer(Computer computer) {
        return computerInstance.create(computer);
    }

    /**
     * Delete a computer.
     * @param id A Long containing the computer's id
     * @return the number of rows deleted
     */
    public int deleteComputer(Long id) {
        return computerInstance.deleteById(id);
    }

    /**
     * Update a computer.
     * @param computer to update
     * @return the updated computer
     */
    public Computer update(Computer computer) {
         return computerInstance.update(computer);
    }

}
