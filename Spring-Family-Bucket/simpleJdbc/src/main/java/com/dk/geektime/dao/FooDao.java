package com.dk.geektime.dao;

import com.dk.geektime.entity.Foo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author Kay
 */
@Slf4j
@Repository
public class FooDao {
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public void setSimpleJdbcInsert(SimpleJdbcInsert simpleJdbcInsert) {
        this.simpleJdbcInsert = simpleJdbcInsert;
    }

    public void insertData() {
        Arrays.asList("A", "B").forEach(data -> jdbcTemplate.update("INSERT INTO FOO (BAR) VALUES (?)", data));

        HashMap<String, String> row = new HashMap<>(1);
        row.put("BAR", "C");
        Number number = simpleJdbcInsert.executeAndReturnKey(row);
        log.info("ID of C:{}", number.longValue());
    }

    public void listData()  {
        log.info("Count: {}", jdbcTemplate.queryForList("SELECT COUNT(*) FROM FOO", Long.class));
        List<String> list = jdbcTemplate.queryForList("SELECT BAR FROM FOO", String.class);
        list.forEach(item -> log.info("Bar:{}", item));

        List<Foo> fooList = jdbcTemplate.query("SELECT * FROM FOO", (resultSet, i) -> Foo.builder()
                .id(resultSet.getLong(1))
                .bar(resultSet.getString(2))
                .build());
        fooList.forEach(foo -> log.info("Foo: {}", foo));
    }
}
