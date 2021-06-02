package com.excilys.formation.java.cdb.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * Represents a mysql database connection.
 * @author Laetitia Tureau
 */
public final class DBConnexion {

    private HikariDataSource ds;
    public static DBConnexion instance;
    private static final String URL_PROPERTIES = "/db.properties";

    /**
     * Creates a connexion to database.
     * @param hikarids the datasource
     */
    private DBConnexion(HikariDataSource hikarids) {
        this.ds = hikarids;
    }

    /**
     * Retrieve the connexion to database.
     * @return database connection object
     */
    public static synchronized DBConnexion getInstance() {
        // TODO renvoyer une exception qui encapsule les exceptions liées a sql ou jdbc
        // afin de masquer les exceptions specifique propres au DAO
        if (instance == null) {
            instance = new DBConnexion(new HikariDataSource(new HikariConfig(URL_PROPERTIES)));
        }
        return instance;
    }

    /**
     * Retrieve connection to database.
     * @return connection
     * @throws SQLException exception
     */
    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
