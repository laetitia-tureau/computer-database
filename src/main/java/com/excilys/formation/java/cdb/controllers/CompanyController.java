package com.excilys.formation.java.cdb.controllers;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.formation.java.cdb.models.Company;
import com.excilys.formation.java.cdb.services.CompanyService;

@Component
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    /**
     * Retrieve all companies.
     * @return list of companies
     */
    public List<Company> getCompanies() {
        return companyService.getCompanies();
    }

    /**
     * Delete a company.
     * @param idCompany company's id to remove
     */
    public void deleteCompany(String idCompany) {
        if (idCompany != null && StringUtils.isNumeric(idCompany)) {
            companyService.deleteCompany(Long.parseLong(idCompany));
        }
    }
}
