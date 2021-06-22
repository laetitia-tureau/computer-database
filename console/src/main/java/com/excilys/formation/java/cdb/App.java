package com.excilys.formation.java.cdb;

import java.sql.SQLException;
import java.text.ParseException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.formation.java.cdb.config.AppConfig;
import com.excilys.formation.java.cdb.daos.CompanyDAO;
import com.excilys.formation.java.cdb.daos.CompanyRepository;
import com.excilys.formation.java.cdb.daos.ComputerDAO;
import com.excilys.formation.java.cdb.daos.ComputerRepository;
import com.excilys.formation.java.cdb.console.UserInterface;

public class App {

    /**
     * Runs the application.
     * @param args An array of strings
     * @throws SQLException for crud operations
     * @throws ParseException when parsing a date
     */
    public static void main(String[] args) throws SQLException, ParseException {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        ComputerRepository computerRepo = context.getBean(ComputerRepository.class);
        CompanyRepository companyRepo = context.getBean(CompanyRepository.class);
        ComputerDAO computerDAO = context.getBean(ComputerDAO.class);
        CompanyDAO companyDAO = context.getBean(CompanyDAO.class);
        UserInterface cli = new UserInterface(computerRepo, companyRepo, computerDAO, companyDAO);
        cli.start();
    }

}
