package com.excilys.formation.java.cdb.persistence.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.excilys.formation.java.cdb.persistence.MysqlConnect;

/** Represents a company DAO.
 * @author Laetitia Tureau
 */
public class CompanyDAO {

    /** The connexion to mySQL database. */
    private MysqlConnect mysqlConnect = MysqlConnect.getDbConnection();

    /** Represents query to retrieve all companies. */
    private static final String ALL_COMPANIES = "SELECT * FROM company";

    /** Represents query to retrieve a specific company. */
    private static final String FIND_COMPANY = "SELECT * FROM company WHERE id = ?";

    /** Creates a DAO to company operations into database. */
    public CompanyDAO() { }

    /**
     * Retrieve all the companies in the database.
     * @throws SQLException for database access error, or closed Connection, or resulting ResultSet
     * @return A ResultSet as a result of the executed query
     */
    public ResultSet getAllCompanies() throws SQLException {
        Statement stmt = mysqlConnect.connexion.createStatement();
        return stmt.executeQuery(ALL_COMPANIES);
    }

    /**
     * Retrieve a company.
     * @param id the company's id
     * @throws SQLException for database access error, or closed Connection or PreparedStatement, or wrong match with setter
     * @return A ResultSet as a result of the executed query
     */
    public ResultSet findById(Long id) throws SQLException {
        PreparedStatement stmt = mysqlConnect.connexion.prepareStatement(FIND_COMPANY);
        stmt.setLong(1, id);
        return stmt.executeQuery();
    }

}
