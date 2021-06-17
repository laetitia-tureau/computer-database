package com.excilys.formation.java.cdb.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.formation.java.cdb.models.Computer;
import com.excilys.formation.java.cdb.persistence.daos.CompanyDAO;
import com.excilys.formation.java.cdb.persistence.daos.ComputerDAO;
import com.excilys.formation.java.cdb.persistence.daos.ComputerRepository;

/**
 * Represents a computer service.
 * @author Laetitia Tureau
 */
@Service
public class ComputerService {

    @Autowired
    private ComputerRepository computerRepository;

    /**
     * Retrieve all the computers in the database.
     * @return a list of computers
     */
    public List<Computer> getComputers() {
        return this.computerRepository.findAll();
    }

    /**
     * Retrieve all the computers in the database.
     * @param page current page
     * @return a list of computers
     */
    public List<Computer> getPaginatedComputers(Pagination page) {
        //return computerInstance.getPaginatedComputers(page);
        return null;
    }

    /**
     * Find all computers matching given criteria.
     * @param criteria represents the search criteria
     * @return a list of computers matching the criteria
     */
    public List<Computer> findByCriteria(SearchCriteria criteria) {
        //return computerInstance.findByCriteria(criteria);
        return null;
    }

    /**
     * Retrieve a computer with a specific id.
     * @param id the computer's id
     * @return a computer or throw exception
     */
    public Computer findById(Long id) {
        return this.computerRepository.getById(id);
    }

    /**
     * Delete a computer.
     * @param id A Long containing the computer's id
     * @return the number of rows deleted
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
