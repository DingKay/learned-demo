package com.dk;

import com.dk.jdbc.suport.CustomDuplicateSqlException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Kay
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ErrorCodeApplicationTest {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Test(expected = CustomDuplicateSqlException.class)
    public void testThrowingCustomException() {
        jdbcTemplate.execute("INSERT INTO FOO (ID, BAR) VALUES (1, 'A')");
        jdbcTemplate.execute("INSERT INTO FOO (ID, BAR) VALUES (1, 'B')");
    }

}
