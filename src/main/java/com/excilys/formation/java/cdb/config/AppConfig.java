package com.excilys.formation.java.cdb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan({ "com.excilys.formation.java.cdb.services", "com.excilys.formation.java.cdb.controllers",
        "com.excilys.formation.java.cdb.persistence.daos", "com.excilys.formation.java.cdb.mappers",
        "com.excilys.formation.java.cdb.servlets" })
public class AppConfig {
    @Bean
    public HikariDataSource getDataSource() {
        return new HikariDataSource(new HikariConfig("/db.properties"));
    }
}
