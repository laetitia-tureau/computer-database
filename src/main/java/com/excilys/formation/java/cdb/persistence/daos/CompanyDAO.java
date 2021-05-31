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

import com.excilys.formation.java.cdb.mappers.CompanyMapper;
import com.excilys.formation.java.cdb.models.Company;
import com.excilys.formation.java.cdb.persistence.DBConnexion;
import com.excilys.formation.java.cdb.services.CompanyService;
import com.excilys.formation.java.cdb.services.Pagination;

/** Represents a company DAO.
 * @author Laetitia Tureau
 */
public class CompanyDAO {

    /** The connexion to mySQL database. */
    private DBConnexion dbConnexion;

    /** Represents query to retrieve all companies. */
    private static final String ALL_COMPANIES = "SELECT * FROM company";

    /** Represents query to retrieve a specific company. */
    private static final String FIND_COMPANY = "SELECT * FROM company WHERE id = ?";

    /** Class logger. */
    private static final Logger LOGGER = Logger.getLogger(CompanyService.class);

    /** Creates a DAO to company operations into database. */
    private CompanyDAO() {
        this.dbConnexion = DBConnexion.getInstance();
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
        try (Connection connexion = dbConnexion.getConnection();
                Statement stmt = connexion.createStatement();
                ResultSet resultSet = stmt.executeQuery(ALL_COMPANIES)) {
            while (resultSet.next()) {
                companies.add(CompanyMapper.mapFromResultSet(resultSet));
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
        String withLimit = " LIMIT " + page.getLimit() * (page.getPage() - 1) + "," + page.getLimit();
        try (Connection connexion = dbConnexion.getConnection();
                Statement stmt = connexion.createStatement();
                ResultSet resultSet = stmt.executeQuery(ALL_COMPANIES + withLimit)) {
            while (resultSet.next()) {
                companies.add(CompanyMapper.mapFromResultSet(resultSet));
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
        try (Connection connexion = dbConnexion.getConnection();
                PreparedStatement stmt = connexion.prepareStatement(FIND_COMPANY)) {
            stmt.setLong(1, id);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return Optional.of(CompanyMapper.mapFromResultSet(resultSet));
            }
        } catch (SQLException sqle) {
            LOGGER.error("Erreur lors de l'exécution de la requête", sqle);
        }
        return Optional.empty();
    }

}
