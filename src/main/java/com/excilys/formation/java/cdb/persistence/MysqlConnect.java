package com.excilys.formation.java.cdb.persistence;

import java.sql.Connection;
import java.sql.DriverManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a mysql database connection.
 * @author Laetitia Tureau
 */
public final class MysqlConnect {

    /** Mysql database's connexion. */
    public Connection connexion;

    /** Mysql database. */
    public static MysqlConnect database;

    /** Represents database's url. */
    private static final String URL = "jdbc:mysql://localhost:3306/";

    /** Represents database's name. */
    private static final String DB_NAME = "computer-database-db";

    /** Represents database's driver. */
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    /** Represents database's user. */
    private static final String USER_NAME = "admincdb";

    /** Represents database's password. */
    private static final String PASSWORD = "qwerty1234";

    /** Class logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(MysqlConnect.class);

    /** Creates a connexion to mysql database. */
    private MysqlConnect() {
        try {
            Class.forName(DRIVER).newInstance();
            this.connexion = (Connection) DriverManager.getConnection(URL + DB_NAME, USER_NAME, PASSWORD);
        } catch (Exception sqle) {
            LOGGER.error("Erreur lors de l'exécution de la requête", sqle);
        }
    }

    /**
     * Retrieve the connexion to database.
     * @return MysqlConnect Database connection object
     */
    public static synchronized MysqlConnect getDbConnection() {
        if (database == null) {
            database = new MysqlConnect();
        }
        return database;
    }
}
