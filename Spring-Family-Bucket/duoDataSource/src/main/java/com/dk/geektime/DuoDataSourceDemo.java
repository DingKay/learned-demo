package com.dk.geektime;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;

/**
 * @author Kay
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class, JdbcTemplateAutoConfiguration.class})
@Slf4j
public class DuoDataSourceDemo {
    public static void main(String[] args) {
        SpringApplication.run(DuoDataSourceDemo.class, args);
    }
}
