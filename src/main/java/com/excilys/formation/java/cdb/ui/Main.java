package com.excilys.formation.java.cdb.ui;

import java.sql.SQLException;
import java.text.ParseException;

/** Main class.
 * @author Laetitia Tureau
 */
public class Main {

    /**
     * Main method that start the cli.
     * @param args An array of strings
     * @throws SQLException
     * @throws ParseException
     */
    public static void main(String[] args) throws SQLException, ParseException {
        UserInterface cli = new UserInterface();
        cli.start();
    }

}
