package com.fiap.fastfood.external.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfiguration {

    /*private static final Logger logger = LogManager.getLogger(DatabaseConfiguration.class);

    @Value("${database.connection.url}")
    private String connectionUrl;

    @Value("${database.connection.username}")
    private String username;

    @Value("${database.connection.password}")
    private String password;

    @Value("${database.driver}")
    private String driver;

    @Bean
    public DataSource dataSource(){
        logger.info("Configuring database...");
        logger.info(connectionUrl);

        final var dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(driver);
        dataSourceBuilder.url(connectionUrl);
        dataSourceBuilder.username(username);
        dataSourceBuilder.password(password);

        return dataSourceBuilder.build();
    }*/
}
