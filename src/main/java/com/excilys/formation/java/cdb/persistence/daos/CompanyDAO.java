package com.excilys.formation.java.cdb.persistence.daos;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.formation.java.cdb.models.Company;
import com.excilys.formation.java.cdb.models.Company.CompanyBuilder;
import com.excilys.formation.java.cdb.services.CompanyService;

/** Represents a company DAO.
 * @author Laetitia Tureau
 */
@Repository
public class CompanyDAO {

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParamJdbcTemplate;

    private static final String ALL_COMPANIES = "SELECT id, name FROM company";
    private static final String FIND_COMPANY = "SELECT * FROM company WHERE id=:id";
    private static final String DELETE_COMPANY = "DELETE FROM company WHERE id=:id";
    private static final String DELETE_COMPUTER = "DELETE FROM computer WHERE company_id=:id";
    private static final Logger LOGGER = Logger.getLogger(CompanyService.class);

    RowMapper<Company> rowMapper = (rs, rowNum) -> {
        CompanyBuilder builder = new Company.CompanyBuilder();
        builder.id(rs.getLong("company.id"));
        builder.name(rs.getString("company.name"));
        return builder.build();
    };

    /** Creates a DAO to company operations into database. */
    public CompanyDAO(NamedParameterJdbcTemplate namedParamJdbcTemp, JdbcTemplate jdbcTemp) {
        this.jdbcTemplate = jdbcTemp;
        this.namedParamJdbcTemplate = namedParamJdbcTemp;
    }

    /**
     * Retrieve all the companies in the database.
     * @return a list of companies
     */
    public List<Company> getAllCompanies() {
        return this.jdbcTemplate.query(ALL_COMPANIES, rowMapper);
    }

    /**
     * Retrieve a company with a specific id.
     * @param id the company's id
     * @return An empty Optional if nothing found else a Optional containing a company
     */
    public Optional<Company> findById(Long id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        try {
            return Optional.ofNullable(this.namedParamJdbcTemplate.queryForObject(FIND_COMPANY, params, rowMapper));
        } catch (DataAccessException ex) {
            LOGGER.error(ex.getMessage());
        }
        return Optional.ofNullable(null);
    }

    /**
     * Delete a company in the database.
     * @throws SQLException for database access error, or closed Connection or PreparedStatement, or wrong match with setter
     * @param id the company's id
     * @return the number of rows deleted
     */
    public int deleteCompany(Long id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        try {
            this.namedParamJdbcTemplate.update(DELETE_COMPUTER, params);
            return this.namedParamJdbcTemplate.update(DELETE_COMPANY, params);
        } catch (DataAccessException ex) {
            LOGGER.error(ex.getMessage());
        }
        return 0;
    }

}
