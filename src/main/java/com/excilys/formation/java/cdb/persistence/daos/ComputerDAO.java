package com.excilys.formation.java.cdb.persistence.daos;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.formation.java.cdb.mappers.ComputerRowMapper;
import com.excilys.formation.java.cdb.models.Computer;
import com.excilys.formation.java.cdb.services.ComputerService;
import com.excilys.formation.java.cdb.services.Pagination;
import com.excilys.formation.java.cdb.services.SearchCriteria;

/** Represents a computer DAO.
 * @author Laetitia Tureau
 */
@Repository
public class ComputerDAO {

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParamJdbcTemplate;
    private ComputerRowMapper rowMapper;

    /** Class logger. */
    private static final Logger LOGGER = Logger.getLogger(ComputerService.class);

    /** Represents query to create a computer. */
    private static final String INSERT_COMPUTER = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (:name, :intro, :dist, :company_id)";

    /** Represents query to retrieve all computers. */
    private static final String ALL_COMPUTERS = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, "
            + "company.name FROM computer LEFT JOIN company ON computer.company_id = company.id";

    /** Represents query to retrieve a specific computer. */
    private static final String FIND_COMPUTER = ALL_COMPUTERS + " WHERE computer.id = :id";

    /** Represents query to delete a computer. */
    private static final String DELETE_COMPUTER = "DELETE FROM computer WHERE id = :id";

    /** Represents query to update a computer. */
    private static final String UPDATE_COMPUTER = "UPDATE computer SET name = :name, introduced = :intro, discontinued = :dist, company_id = :company_id WHERE id = :id ";

    /**
     * Creates a DAO to computer operations into database.
     * @param namedParamJdbcTemp jdbc template
     * @param jdbcTemp jdbc template
     */
    public ComputerDAO(NamedParameterJdbcTemplate namedParamJdbcTemp, JdbcTemplate jdbcTemp,
            ComputerRowMapper cptRowMapper) {
        this.jdbcTemplate = jdbcTemp;
        this.namedParamJdbcTemplate = namedParamJdbcTemp;
        this.rowMapper = cptRowMapper;
    }

    /**
     * Retrieve all the computers in the database.
     * @return a list of computers
     */
    public List<Computer> findAll() {
        return this.jdbcTemplate.query(ALL_COMPUTERS, rowMapper);
    }

    /**
     * Retrieve all the computers in the database.
     * @param page current page
     * @return a list of computers
     */
    public List<Computer> getPaginatedComputers(Pagination page) {
        String withLimit = " LIMIT " + page.getItemsPerPage() * (page.getCurrentPage() - 1) + ","
                + page.getItemsPerPage();
        return this.jdbcTemplate.query(ALL_COMPUTERS + withLimit, rowMapper);
    }

    /**
     * Insert a computer in the database.
     * @throws SQLException for database access error, or closed Connection or PreparedStatement, or wrong match with setter
     * @param computer object to create
     * @return the computer saved in database
     */
    public int create(Computer computer) {
        Long companyId = computer.getManufacturer() != null ? computer.getManufacturer().getId() : null;
        Date dateIntro = computer.getIntroduced() != null ? java.sql.Date.valueOf(computer.getIntroduced()) : null;
        Date dateDist = computer.getDiscontinued() != null ? java.sql.Date.valueOf(computer.getDiscontinued()) : null;

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", computer.getName());
        params.addValue("intro", dateIntro);
        params.addValue("dist", dateDist);
        params.addValue("company_id", companyId);

        try {
            return this.namedParamJdbcTemplate.update(INSERT_COMPUTER, params);
        } catch (DataAccessException ex) {
            LOGGER.error(ex.getMessage());
        }
        return 0;
    }

    /**
     * Update a computer in the database.
     * @param computer to update
     * @return the number of rows edited
     */
    public int update(Computer computer) {
        Long companyId = computer.getManufacturer() != null ? computer.getManufacturer().getId() : null;
        Date dateIntro = computer.getIntroduced() != null ? java.sql.Date.valueOf(computer.getIntroduced()) : null;
        Date dateDist = computer.getDiscontinued() != null ? java.sql.Date.valueOf(computer.getDiscontinued()) : null;

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", computer.getId());
        params.addValue("name", computer.getName());
        params.addValue("intro", dateIntro);
        params.addValue("dist", dateDist);
        params.addValue("company_id", companyId);

        try {
            return this.namedParamJdbcTemplate.update(UPDATE_COMPUTER, params);
        } catch (DataAccessException ex) {
            LOGGER.error(ex.getMessage());
        }
        return 0;
    }

    /**
     * Retrieve a computer.
     * @param id the computer's id
     * @return An empty Optional if nothing found else a Optional containing a computer
     */
    public Optional<Computer> findById(Long id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        try {
            return Optional.ofNullable(this.namedParamJdbcTemplate.queryForObject(FIND_COMPUTER, params, rowMapper));
        } catch (DataAccessException ex) {
            LOGGER.error(ex.getMessage());
        }
        return Optional.ofNullable(null);
    }

    /**
     * Delete a computer in the database.
     * @throws SQLException for database access error, or closed Connection or PreparedStatement, or wrong match with setter
     * @param id the computer's id
     * @return the number of rows deleted
     */
    public int deleteById(Long id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        try {
            return this.namedParamJdbcTemplate.update(DELETE_COMPUTER, params);
        } catch (DataAccessException ex) {
            LOGGER.error(ex.getMessage());
        }
        return 0;
    }

    /**
     * Find all computers matching given criteria.
     * @param criteria represents the search criteria (search, order, sort, limit)
     * @return a list of computers matching the criteria
     */
    public List<Computer> findByCriteria(SearchCriteria criteria) {
        List<Computer> computers = new ArrayList<>();
        String query = ALL_COMPUTERS;
        if (StringUtils.isNotBlank(criteria.getItemName())) {
            query += " WHERE computer.name LIKE ? OR company.name LIKE ? ";
        }
        if (StringUtils.isNotBlank(criteria.getOrder()) && StringUtils.isNotBlank(criteria.getSort())) {
            query += " ORDER BY " + criteria.getSort() + " " + criteria.getOrder();
        }
        if (StringUtils.isNotBlank(criteria.getLimit())) {
            query += " LIMIT " + criteria.getLimit();
        }
        query += ";";
        try {
            if (StringUtils.isNotBlank(criteria.getItemName())) {
                return this.jdbcTemplate.query(query, rowMapper, criteria.getItemName(), criteria.getItemName());
            } else {
                return this.jdbcTemplate.query(query, rowMapper);
            }
        } catch (DataAccessException ex) {
            LOGGER.error(ex.getMessage());
        }
        return computers;
    }
}
