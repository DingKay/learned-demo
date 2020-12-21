package com.dk.geektime.dao.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;

/**
 * @author Kay
 */
@Configuration
public class SimpleInsertConfig {
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public SimpleJdbcInsert simpleJdbcInsert() {
        return new SimpleJdbcInsert(dataSource).withTableName("FOO").usingGeneratedKeyColumns("ID");
    }
}
