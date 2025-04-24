package com.example.LibraryManagement.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class CustomDataSourceConfig {

    @Value("${app.datasource.type}")
    private String dbType;

    @Value("${spring.datasource.mysql.url:}")
    private String mysqlUrl;

    @Value("${spring.datasource.mysql.username:}")
    private String mysqlUser;

    @Value("${spring.datasource.mysql.password:}")
    private String mysqlPass;

    @Value("${spring.datasource.h2.url:}")
    private String h2Url;

    @Value("${spring.datasource.h2.username:}")
    private String h2User;

    @Value("${spring.datasource.h2.password:}")
    private String h2Pass;

    @Bean
    public DataSource customDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        if ("mysql".equalsIgnoreCase(dbType)) {
            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
            dataSource.setUrl(mysqlUrl);
            dataSource.setUsername(mysqlUser);
            dataSource.setPassword(mysqlPass);
        } else if ("h2".equalsIgnoreCase(dbType)) {
            dataSource.setDriverClassName("org.h2.Driver");
            dataSource.setUrl(h2Url);
            dataSource.setUsername(h2User);
            dataSource.setPassword(h2Pass);
        } else {
            throw new IllegalArgumentException("Unsupported DB type: " + dbType);
        }

        return dataSource;
    }
}

