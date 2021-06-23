package com.excilys.formation.java.cdb.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

import com.excilys.formation.java.cdb.dtos.CompanyDTO;
import com.excilys.formation.java.cdb.models.Company;
import com.excilys.formation.java.cdb.models.Company.CompanyBuilder;

/**
 * Mapper class for companies.
 * @author Laetitia Tureau
 */
@Component
public class CompanyMapper {

    /**
     * Convert a ResultSet into a Company.
     * @throws SQLException for database access error, or closed ResultSet, or argument passed in ResultSet getters not valid
     * @param result A ResultSet to convert
     * @return the company resulting
     */
    public Company mapFromResultSet(ResultSet result) throws SQLException {
        CompanyBuilder builder = new Company.CompanyBuilder().id(result.getLong(1));
        if (result.getString(2) != null) {
            builder.name(result.getString(2));
        }
        return new Company(builder);
    }

    /**
     * Convert a Company into a CompanyDTO.
     * @param company A Company to convert
     * @return the companyDTO resulting
     */
    public CompanyDTO mapFromModelToDTO(Company company) {
        String id = String.valueOf(company.getId());
        return new CompanyDTO.CompanyBuilderDTO(id, company.getName()).build();
    }

    /**
     * Convert a CompanyDTO into a Company.
     * @param companyDTO A CompanyDTO to convert
     * @return the company resulting
     */
    public Company mapFromDTOtoModel(CompanyDTO companyDTO) {
        Long id = Long.parseLong(companyDTO.getId());
        return new Company.CompanyBuilder(id, companyDTO.getName()).build();
    }

}
