package com.excilys.formation.java.cdb.services;

import java.util.List;
import java.util.Optional;

import com.excilys.formation.java.cdb.models.Company;
import com.excilys.formation.java.cdb.persistence.daos.CompanyDAO;

/**
 * Represents a company service.
 * @author Laetitia Tureau
 */
public class CompanyService {

    /**
     * A DAO instance used to encapsulate the logic for retrieving, saving and updating table company data into the database.
     */
    private static CompanyDAO companyInstance = CompanyDAO.getInstance();

    /**
     * Creates a service to company operations.
     */
    public CompanyService() {
    }

    public void setCompanyInstance(CompanyDAO companyInstance) {
        CompanyService.companyInstance = companyInstance;
    }

    /**
     * Retrieve all the companies in the database.
     * @return a list of companies
     */
    public List<Company> listAll() {
        return companyInstance.getAllCompanies();
    }

    /**
     * Retrieve a company with a specific id.
     * @param id the company's id
     * @return a company or throw exception
     */
    public Company findById(Long id) {
        Optional<Company> opt = companyInstance.findById(id);
        // TODO : create proper exception
        return opt.orElseThrow(RuntimeException::new);
    }

}
