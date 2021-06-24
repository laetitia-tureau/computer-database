package com.excilys.formation.java.cdb.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.formation.java.cdb.models.Company;
import com.excilys.formation.java.cdb.daos.CompanyRepository;
import com.excilys.formation.java.cdb.daos.ComputerRepository;

/**
 * Represents a company service.
 * @author Laetitia Tureau
 */
@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ComputerRepository computerRepository;

    /**
     * Creates a company service.
     * @param companyRepo company jpa repository
     * @param computerRepo computer jpa repository
     */
    public CompanyService(CompanyRepository companyRepo, ComputerRepository computerRepo) {
        super();
        this.companyRepository = companyRepo;
        this.computerRepository = computerRepo;
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
        this.computerRepository.deleteByManufacturer(this.companyRepository.getById(id));
        this.companyRepository.deleteById(id);
    }
}
