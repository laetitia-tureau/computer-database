package com.excilys.formation.java.cdb.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.formation.java.cdb.dtos.CompanyDTO;
import com.excilys.formation.java.cdb.mappers.CompanyMapper;
import com.excilys.formation.java.cdb.models.Company;
import com.excilys.formation.java.cdb.services.CompanyService;

@Component
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyMapper companyMapper;

    /**
     * Retrieve all companies.
     * @return list of companies
     */
    public List<CompanyDTO> getCompanies() {
        List<Company> companies = companyService.getCompanies();
        return companies.stream().map(c -> companyMapper.mapFromModelToDTO(c)).collect(Collectors.toList());
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
