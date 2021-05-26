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

    /** Creates a connexion to database. */
    private DBConnexion(HikariDataSource HikariDS) {
        this.ds = HikariDS;
    }

    /**
     * Retrieve the connexion to database.
     * @return MysqlConnect Database connection object
     */
    public static synchronized DBConnexion getInstance() {
        // TODO throw exception
        if (instance == null) {
            instance = new DBConnexion(new HikariDataSource(new HikariConfig(URL_PROPERTIES)));
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
