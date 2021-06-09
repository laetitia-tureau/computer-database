package com.excilys.formation.java.cdb.persistence.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.formation.java.cdb.mappers.CompanyMapper;
import com.excilys.formation.java.cdb.models.Company;
import com.excilys.formation.java.cdb.services.CompanyService;
import com.excilys.formation.java.cdb.services.Pagination;
import com.zaxxer.hikari.HikariDataSource;

/** Represents a company DAO.
 * @author Laetitia Tureau
 */
@Repository
public class CompanyDAO {

    /** The connexion to mySQL database. */
    @Autowired
    private HikariDataSource dataSource;

    @Autowired
    private CompanyMapper companyMapper;

    /** Represents query to retrieve all companies. */
    private static final String ALL_COMPANIES = "SELECT * FROM company";

    /** Represents query to retrieve a specific company. */
    private static final String FIND_COMPANY = "SELECT * FROM company WHERE id = ?";

    /** Represents query to delete a company. */
    private static final String DELETE_COMPANY = "DELETE FROM company WHERE id = ?";

    /** Represents query to delete a computer. */
    private static final String DELETE_COMPUTER = "DELETE FROM computer WHERE company_id = ?";

    /** Class logger. */
    private static final Logger LOGGER = Logger.getLogger(CompanyService.class);

    /** Creates a DAO to company operations into database. */
    private CompanyDAO() {
    }

    public static CompanyDAO getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final CompanyDAO INSTANCE = new CompanyDAO();
    }

    /**
     * Retrieve all the companies in the database.
     * @return a list of companies
     */
    public List<Company> getAllCompanies() {
        List<Company> companies = new ArrayList<>();
        try (Connection connexion = dataSource.getConnection();
                Statement stmt = connexion.createStatement();
                ResultSet resultSet = stmt.executeQuery(ALL_COMPANIES)) {
            while (resultSet.next()) {
                companies.add(companyMapper.mapFromResultSet(resultSet));
            }
        } catch (SQLException sqle) {
            LOGGER.error("Erreur lors de l'exécution de la requête", sqle);
        }
        return companies;
    }

    /**
     * Retrieve all the companies in the database.
     * @param page current page
     * @return a list of companies
     */
    public List<Company> getPaginatedCompanies(Pagination page) {
        List<Company> companies = new ArrayList<>();
        String withLimit = " LIMIT " + page.getItemsPerPage() * (page.getCurrentPage() - 1) + ","
                + page.getItemsPerPage();
        try (Connection connexion = dataSource.getConnection();
                Statement stmt = connexion.createStatement();
                ResultSet resultSet = stmt.executeQuery(ALL_COMPANIES + withLimit)) {
            while (resultSet.next()) {
                companies.add(companyMapper.mapFromResultSet(resultSet));
            }
        } catch (SQLException sqle) {
            LOGGER.error("Erreur lors de l'exécution de la requête", sqle);
        }
        return companies;
    }

    /**
     * Retrieve a company with a specific id.
     * @param id the company's id
     * @return An empty Optional if nothing found else a Optional containing a company
     */
    public Optional<Company> findById(Long id) {
        try (Connection connexion = dataSource.getConnection();
                PreparedStatement stmt = connexion.prepareStatement(FIND_COMPANY)) {
            stmt.setLong(1, id);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return Optional.of(companyMapper.mapFromResultSet(resultSet));
            }
        } catch (SQLException sqle) {
            LOGGER.error("Erreur lors de l'exécution de la requête", sqle);
        }
        return Optional.empty();
    }

    /**
     * Delete a company in the database.
     * @throws SQLException for database access error, or closed Connection or PreparedStatement, or wrong match with setter
     * @param id the company's id
     * @return the number of rows deleted
     */
    public int deleteCompany(Long id) {
        try (Connection connexion = dataSource.getConnection()) {
            connexion.setAutoCommit(false);
            try (PreparedStatement companyPs = connexion.prepareStatement(DELETE_COMPANY);
                    PreparedStatement computerPs = connexion.prepareStatement(DELETE_COMPUTER)) {
                computerPs.setLong(1, id);
                companyPs.setLong(1, id);
                computerPs.executeUpdate();
                return companyPs.executeUpdate();
            } catch (SQLException sqle) {
                connexion.rollback();
                LOGGER.error("Erreur lors de l'exécution de la requête", sqle);
            } finally {
                connexion.setAutoCommit(true);
            }
        } catch (SQLException sqle) {
            LOGGER.error("Erreur lors de l'exécution de la requête", sqle);
        }
        return 0;
    }

}
