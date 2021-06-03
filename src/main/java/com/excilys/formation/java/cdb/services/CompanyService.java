package com.excilys.formation.java.cdb.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.excilys.formation.java.cdb.exceptions.MyPersistenceException;
import com.excilys.formation.java.cdb.models.Company;
import com.excilys.formation.java.cdb.models.Computer;
import com.excilys.formation.java.cdb.persistence.daos.CompanyDAO;
import com.excilys.formation.java.cdb.persistence.daos.ComputerDAO;

/**
 * Represents a company service.
 * @author Laetitia Tureau
 */
public class CompanyService {

    /**
     * A DAO instance used to encapsulate the logic for retrieving, saving and updating table company data into the database.
     */
    private static CompanyDAO companyInstance = CompanyDAO.getInstance();

    public void setCompanyInstance(CompanyDAO companyInstance) {
        CompanyService.companyInstance = companyInstance;
    }

    /**
     * Retrieve all the companies in the database.
     * @return a list of companies
     */
    public List<Company> getCompanies() {
        return companyInstance.getAllCompanies();
    }

    /**
     * Retrieve all the companies in the database.
     * @param page current page
     * @return a list of companies
     */
    public List<Company> getPaginatedCompanies(Pagination page) {
        return companyInstance.getPaginatedCompanies(page);
    }

    /**
     * Retrieve a company with a specific id.
     * @param id the company's id
     * @return a company or throw exception
     */
    public Company findById(Long id) {
        Optional<Company> opt = companyInstance.findById(id);
        return opt.orElseThrow(MyPersistenceException::new);
        // return opt.orElse(null);
    }

    /**
     * Delete a company and its computers.
     * @param id A Long containing the company's id
     * @return the number of rows deleted
     */
    public int deleteCompany(Long id) {
        findById(id);
        List<Computer> computers = new ArrayList<>(computerInstance.getComputers());
        computers.removeIf(c -> c.getManufacturer() == null);
        Predicate<Computer> p = c -> c.getManufacturer().getId() == id;
        List<Computer> cs = computers.stream().filter(p).collect(Collectors.toList());
        cs.stream().forEach(c -> computerInstance.deleteComputer(c.getId()));
        return companyInstance.deleteCompany(id);
    }

    /**
     * Initialize ComputerDAO instance.
     * @param computerInstance the instance
     */
    public void setComputerInstance(ComputerDAO computerInstance) {
        CompanyService.computerInstance.setComputerInstance(computerInstance);
    }

    private static ComputerService computerInstance = new ComputerService();
}
