package com.excilys.formation.java.cdb.persistence.daos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.formation.java.cdb.mappers.ComputerMapper;
import com.excilys.formation.java.cdb.models.Company;
import com.excilys.formation.java.cdb.models.Computer;
import com.excilys.formation.java.cdb.models.Computer.ComputerBuilder;
import com.excilys.formation.java.cdb.services.ComputerService;
import com.excilys.formation.java.cdb.services.Pagination;
import com.excilys.formation.java.cdb.services.SearchCriteria;
import com.zaxxer.hikari.HikariDataSource;

/** Represents a computer DAO.
 * @author Laetitia Tureau
 */
@Repository
public class ComputerDAO {

    /** The connexion to mySQL database. */
    @Autowired
    private HikariDataSource dataSource;

    @Autowired
    private ComputerMapper computerMapper;

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParamJdbcTemplate;

    /** Class logger. */
    private static final Logger LOGGER = Logger.getLogger(ComputerService.class);

    /** Represents query to create a computer. */
    private static final String INSERT_COMPUTER = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?,?,?,?)";

    /** Represents query to retrieve all computers. */
    private static final String ALL_COMPUTERS = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, "
            + "company.name FROM computer LEFT JOIN company ON computer.company_id = company.id";

    /** Represents query to retrieve a specific computer. */
    private static final String FIND_COMPUTER = ALL_COMPUTERS + " WHERE computer.id = :id";

    /** Represents query to delete a computer. */
    private static final String DELETE_COMPUTER = "DELETE FROM computer WHERE id = :id";

    private static final String UPDATE_COMPUTER = "UPDATE computer SET name = :name, introduced = :intro, discontinued = :dist, company_id = :company_id WHERE id = :id ";

    /** Creates a DAO to computer operations into database. */
    public ComputerDAO(NamedParameterJdbcTemplate namedParamJdbcTemp, JdbcTemplate jdbcTemp) {
        this.jdbcTemplate = jdbcTemp;
        this.namedParamJdbcTemplate = namedParamJdbcTemp;
    }

    RowMapper<Computer> rowMapper = (rs, rowNum) -> {
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
    };

    /**
     * Retrieve all the computers in the database.
     * @return a list of computers
     */
    public List<Computer> getAllComputers() {
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
    public Computer createComputer(Computer computer) {
        ComputerBuilder builder = new Computer.ComputerBuilder().name(computer.getName());
        try (Connection connexion = dataSource.getConnection();
                PreparedStatement stmt = connexion.prepareStatement(INSERT_COMPUTER)) {
            int i = 1;
            stmt.setString(i++, computer.getName());
            if (computer.getIntroduced() != null) {
                stmt.setDate(i++, java.sql.Date.valueOf(computer.getIntroduced()));
                builder.introduced(computer.getIntroduced());
            } else {
                stmt.setNull(i++, 0);
            }
            if (computer.getDiscontinued() != null) {
                stmt.setDate(i++, java.sql.Date.valueOf(computer.getDiscontinued()));
                builder.introduced(computer.getDiscontinued());
            } else {
                stmt.setNull(i++, 0);
            }
            if (computer.getManufacturer() != null) {
                if (computer.getManufacturer().getId() != null) {
                    stmt.setLong(i++, computer.getManufacturer().getId());
                    builder.manufacturer(computer.getManufacturer());
                }
            } else {
                stmt.setNull(i++, 0);
            }
            stmt.executeUpdate();
        } catch (SQLException sqle) {
            LOGGER.error("Erreur lors de l'exécution de la requête", sqle);
        }
        return computer;
    }

    public int update(Computer computer) {
        Optional<Long> optCompanyId = Optional.ofNullable(computer.getManufacturer().getId());
        Optional<Date> optIntro = Optional.ofNullable(java.sql.Date.valueOf(computer.getIntroduced()));
        Optional<Date> optDist = Optional.ofNullable(java.sql.Date.valueOf(computer.getDiscontinued()));

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", computer.getId());
        params.addValue("name", computer.getName());
        params.addValue("intro", optIntro.orElse(null));
        params.addValue("dist", optDist.orElse(null));
        params.addValue("company_id", optCompanyId.orElse(null));

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
    public int deleteComputer(Long id) {
        try (Connection connexion = dataSource.getConnection();
                PreparedStatement stmt = connexion.prepareStatement(DELETE_COMPUTER)) {
            stmt.setLong(1, id);
            return stmt.executeUpdate();
        } catch (SQLException sqle) {
            LOGGER.error("Erreur lors de l'exécution de la requête", sqle);
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
        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            if (StringUtils.isNotBlank(criteria.getItemName())) {
                preparedStatement.setString(1, "%" + criteria.getItemName() + "%");
                preparedStatement.setString(2, "%" + criteria.getItemName() + "%");
            }
            try (ResultSet result = preparedStatement.executeQuery()) {
                while (result.next()) {
                    computers.add(computerMapper.mapFromResultSet(result));
                }
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return computers;
    }
}
