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
     * A DAO used to encapsulate the logic for retrieving, saving and updating table company data into the database.
     */
    private CompanyDAO companyDAO;

    /**
     * Creates a service to company operations.
     */
    public CompanyService() {
        this.companyDAO = new CompanyDAO();
    }

    /**
     * Retrieve all the companies in the database.
     * @return a list of companies
     */
    public List<Company> listAll() {
        return companyDAO.getAllCompanies();
    }

    /**
     * Retrieve a company with a specific id.
     * @param id the company's id
     * @return An empty Optional if nothing found else a Optional containing a company
     */
    public Optional<Company> findById(Long id) {
        return companyDAO.findById(id);
    }

}
