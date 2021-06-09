package com.excilys.formation.java.cdb;

import java.sql.SQLException;
import java.text.ParseException;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.formation.java.cdb.config.AppConfig;
import com.excilys.formation.java.cdb.persistence.daos.CompanyDAO;
import com.excilys.formation.java.cdb.persistence.daos.ComputerDAO;
import com.excilys.formation.java.cdb.ui.UserInterface;

public class App {

    private static Logger log = Logger.getLogger(App.class);

    /**
     * Runs the application.
     * @param args An array of strings
     * @throws SQLException for crud operations
     * @throws ParseException when parsing a date
     */
    public static void main(String[] args) throws SQLException, ParseException {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        CompanyDAO companyDAO = context.getBean(CompanyDAO.class);
        ComputerDAO computerDAO = context.getBean(ComputerDAO.class);
        UserInterface cli = new UserInterface(computerDAO, companyDAO);
        cli.start();
    }

}
