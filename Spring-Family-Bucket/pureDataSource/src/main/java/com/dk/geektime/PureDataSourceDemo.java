package com.dk.geektime;

import javafx.application.Application;
import javafx.application.Platform;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Properties;

/**
 * @author Kay
 */
@Configuration
@EnableTransactionManagement
public class PureDataSourceDemo {
    DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static void main(String[] args) throws SQLException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        showBeans(context);
        dataSourceDemo(context);
    }

//    @Bean(destroyMethod = "close", name = "DataSource")
//    public DataSource getDataSource() throws Exception {
//        Properties properties = new Properties();
//        properties.setProperty("username", "sa");
//        properties.setProperty("url", "jdbc:h2:mem:test");
//        properties.setProperty("password", "123456");
//        properties.setProperty("driverClassName", "org.h2.Driver");
//        return BasicDataSourceFactory.createDataSource(properties);
//    }

    @Bean
    public PlatformTransactionManager transactionManager() throws Exception {
        return new DataSourceTransactionManager(dataSource);
    }

    public static void showBeans(ApplicationContext context) {
        System.out.println(Arrays.toString(context.getBeanDefinitionNames()));
    }

    public static void dataSourceDemo(ApplicationContext context) throws SQLException {
        PureDataSourceDemo pureDataSourceDemo = context.getBean("pureDataSourceDemo", PureDataSourceDemo.class);
        pureDataSourceDemo.showDataSource();
    }

    public void showDataSource() throws SQLException {
        System.out.println(dataSource.toString());
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }
}
