package com.dk.geektime;

import com.dk.geektime.dao.FooService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author Kay
 */
@SpringBootApplication
@Slf4j
public class DeclareTransactionDemo implements CommandLineRunner {
    private FooService fooService;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setFooService(FooService fooService) {
        this.fooService = fooService;
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static void main(String[] args) {
        SpringApplication.run(DeclareTransactionDemo.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        fooService.insertDataWithoutTransaction();
        log.info("AAA COUNT: {}", getCount("AAA"));
        fooService.insertDataTransaction();
        log.info("BBB COUNT: {}", getCount("BBB"));
        try {
            fooService.insertDataWhenExceptionRollback();
        } catch (Exception e) {
            log.info("CCC COUNT: {}", getCount("CCC"));
        }
        try {
            fooService.insertDataWithoutTransactionByInvokeOtherMethod();
        } catch (Exception e) {
            log.info("DDD COUNT: {}", getCount("DDD"));
        }
        try {
            fooService.insertDataWithoutTransactionBySelfInvokeMethod();
        } catch (Exception e) {
            log.info("DDD COUNT: {}", getCount("DDD"));
        }
        try {
            fooService.insertDataByProxyObjInvoke();
        } catch (Exception e) {
            log.info("DDD COUNT: {}", getCount("DDD"));
        }
        try {
            fooService.insertDataWithTransaction();
        } catch (Exception e) {
            log.info("DDD COUNT: {}", getCount("DDD"));
        }

    }

    public long getCount(String word) {
        return (long)jdbcTemplate.queryForList("SELECT COUNT(*) AS C FROM FOO WHERE BAR = ?", word).get(0).get("C");
    }
}
