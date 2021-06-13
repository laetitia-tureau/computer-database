package com.excilys.formation.java.cdb.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.formation.java.cdb.models.Company;
import com.excilys.formation.java.cdb.persistence.daos.CompanyDAO;

/**
 * Represents a company service.
 * @author Laetitia Tureau
 */
@Service
public class CompanyService {

    /**
     * A DAO instance used to encapsulate the logic for retrieving, saving and updating table company data into the database.
     */
    @Autowired
    private CompanyDAO companyInstance;

    public void setCompanyInstance(CompanyDAO companyInstance) {
        this.companyInstance = companyInstance;
    }

    /**
     * Retrieve all the companies in the database.
     * @return a list of companies
     */
    public List<Company> getCompanies() {
        return companyInstance.findAll();
    }

    /**
     * Retrieve a company with a specific id.
     * @param id the company's id
     * @return a company or throw exception
     */
    public Company findById(Long id) {
        Optional<Company> opt = companyInstance.findById(id);
        return opt.orElse(null);
    }

    /**
     * Delete a company and its computers.
     * @param id A Long containing the company's id
     * @return the number of rows deleted
     */
    public int deleteCompany(Long id) {
        return companyInstance.deleteById(id);
    }
}
