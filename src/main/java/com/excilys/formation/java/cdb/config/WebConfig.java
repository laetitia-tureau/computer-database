package com.excilys.formation.java.cdb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableWebMvc
@ComponentScan({ "com.excilys.formation.java.cdb.services", "com.excilys.formation.java.cdb.controllers",
        "com.excilys.formation.java.cdb.persistence.daos", "com.excilys.formation.java.cdb.mappers",
        "com.excilys.formation.java.cdb.persistence", "com.excilys.formation.java.cdb.servlets",
        "com.excilys.formation.java.cdb.validator" })
public class WebConfig implements WebMvcConfigurer {

    /**
     * Build a ViewResolver maps view names to actual views.
     * @return a ViewResolver
     */
    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver bean = new InternalResourceViewResolver();
        bean.setViewClass(JstlView.class);
        bean.setPrefix("/WEB-INF/views/");
        bean.setSuffix(".jsp");
        return bean;
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

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
