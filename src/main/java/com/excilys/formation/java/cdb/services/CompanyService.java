package com.excilys.formation.java.cdb.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.formation.java.cdb.models.Company;
import com.excilys.formation.java.cdb.persistence.daos.CompanyRepository;

/**
 * Represents a company service.
 * @author Laetitia Tureau
 */
@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    /**
     * Creates a company service.
     * @param companyDAO jpa repository
     */
    public CompanyService(CompanyRepository companyDAO) {
        super();
        this.companyRepository = companyDAO;
    }

    /**
     * Retrieve all the companies in the database.
     * @return a list of companies
     */
    public List<Company> getCompanies() {
        return this.companyRepository.findAll();
    }

    /**
     * Retrieve a company with a specific id.
     * @param id the company's id
     * @return a company or throw exception
     */
    public Company findById(Long id) {
        return this.companyRepository.getById(id);
    }

    /**
     * Delete a company and its computers.
     * @param id A Long containing the company's id
     */
    @Transactional
    public void deleteCompany(Long id) {
        this.companyRepository.deleteById(id);
    }
}
