package com.dk.geektime.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * @author Kay
 */
@Configuration("application.properties")
@Slf4j
public class DuoDataSourceConfig {
    @Bean
    @ConfigurationProperties("foo.datasource")
    public DataSourceProperties fooDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource fooDataSource() {
        DataSourceProperties properties = fooDataSourceProperties();
        log.info("foo DataSource: {}", properties.getUrl());
        return properties.initializeDataSourceBuilder().build();
    }

    @Bean
    public PlatformTransactionManager fooTransactionManager() {
        return new DataSourceTransactionManager(fooDataSource());
    }

    @Bean
    @ConfigurationProperties("bar.datasource")
    public DataSourceProperties barDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource barDataSource() {
        DataSourceProperties properties = barDataSourceProperties();
        log.info("bar DataSource: {}", properties.getUrl());
        return properties.initializeDataSourceBuilder().build();
    }

    @Bean
    public PlatformTransactionManager barTransactionManager() {
        return new DataSourceTransactionManager(barDataSource());
    }
}
