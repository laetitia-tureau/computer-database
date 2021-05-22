package com.excilys.formation.java.cdb.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.formation.java.cdb.models.Company;
import com.excilys.formation.java.cdb.models.Company.CompanyBuilder;

/**
 * Mapper class for companies.
 * @author Laetitia Tureau
 */
public class CompanyMapper {

    /**
     * Convert a ResultSet into a Company.
     * @throws SQLException for database access error, or closed ResultSet, or argument passed in ResultSet getters not valid
     * @param result A ResultSet to convert
     * @return the company resulting
     */
    public static Company mapFromResultSet(ResultSet result) throws SQLException {
        CompanyBuilder builder = new Company.CompanyBuilder(result.getLong(1));
        if (result.getString(2) != null) {
            builder.name(result.getString(2));
        }
        return new Company(builder);
    }

}
