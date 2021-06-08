package com.excilys.formation.java.cdb;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.formation.java.cdb.config.AppConfig;
import com.excilys.formation.java.cdb.persistence.daos.CompanyDAO;

public class App {

    private static Logger log = Logger.getLogger(App.class);

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        CompanyDAO companyDao = context.getBean(CompanyDAO.class);
        log.debug(companyDao.findById(1L));
    }

}
