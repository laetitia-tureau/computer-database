package com.excilys.formation.java.cdb;

import java.sql.SQLException;
import java.text.ParseException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.formation.java.cdb.config.AppConfig;
import com.excilys.formation.java.cdb.persistence.daos.CompanyDAO;
import com.excilys.formation.java.cdb.persistence.daos.CompanyRepository;
import com.excilys.formation.java.cdb.persistence.daos.ComputerDAO;
import com.excilys.formation.java.cdb.persistence.daos.ComputerRepository;
import com.excilys.formation.java.cdb.ui.UserInterface;

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
        UserInterface cli = new UserInterface(computerRepo, companyRepo);
        cli.start();
    }

}
