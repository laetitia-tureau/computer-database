package com.excilys.formation.java.cdb.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.excilys.formation.java.cdb.models.Company;
import com.excilys.formation.java.cdb.models.Company.CompanyBuilder;

@Component
public class CompanyRowMapper implements RowMapper<Company> {

    @Override
    public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
        CompanyBuilder builder = new Company.CompanyBuilder();
        builder.id(rs.getLong("company.id"));
        builder.name(rs.getString("company.name"));
        return builder.build();
    }

}
