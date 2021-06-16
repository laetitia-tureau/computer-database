package com.excilys.formation.java.cdb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan({ "com.excilys.formation.java.cdb.services",
        "com.excilys.formation.java.cdb.persistence.daos", "com.excilys.formation.java.cdb.mappers",
        "com.excilys.formation.java.cdb.servlets", "com.excilys.formation.java.cdb.validator" })
public class AppConfig {
    @Bean
    public HikariDataSource getDataSource() {
        return new HikariDataSource(new HikariConfig("/db.properties"));
    }

    @Bean
    public JdbcTemplate getJdbcTemplate(HikariDataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate(HikariDataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }
}
