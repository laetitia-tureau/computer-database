package com.excilys.formation.java.cdb.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.excilys.formation.java.cdb.models.Company;
import com.excilys.formation.java.cdb.models.Computer;
import com.excilys.formation.java.cdb.models.Computer.ComputerBuilder;

@Component
public class ComputerRowMapper implements RowMapper<Computer> {

    @Override
    public Computer mapRow(ResultSet rs, int rowNum) throws SQLException {
        ComputerBuilder builder = new Computer.ComputerBuilder();
        builder.id(rs.getLong("computer.id"));
        builder.name(rs.getString("computer.name"));
        if (rs.getDate("computer.introduced") != null) {
            builder.introduced(rs.getDate("computer.introduced").toLocalDate());
        }
        if (rs.getDate("computer.discontinued") != null) {
            builder.discontinued(rs.getDate("computer.discontinued").toLocalDate());
        }
        if (rs.getInt("computer.company_id") != 0) {
            Company company = new Company.CompanyBuilder().id(rs.getLong("computer.company_id"))
                    .name(rs.getString("company.name")).build();
            builder.manufacturer(company);
        }
        return builder.build();
    }

}
