package com.excilys.formation.java.cdb.controllers;

import java.util.List;

import com.excilys.formation.java.cdb.models.Company;
import com.excilys.formation.java.cdb.services.CompanyService;

public class CompanyController {
    public static List<Company> getCompanies() {
        return new CompanyService().getCompanies();
    }
}
