package com.excilys.formation.java.cdb.controllers;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.excilys.formation.java.cdb.models.Company;
import com.excilys.formation.java.cdb.services.CompanyService;

public class CompanyController {
    /**
     * Retrieve all companies.
     * @return list of companies
     */
    public static List<Company> getCompanies() {
        return new CompanyService().getCompanies();
    }

    /**
     * Delete a company.
     * @param idCompany company's id to remove
     */
    public static void deleteCompany(String idCompany) {
        if (idCompany != null && StringUtils.isNumeric(idCompany)) {
            new CompanyService().deleteCompany(Long.parseLong(idCompany));
        }
    }
}
