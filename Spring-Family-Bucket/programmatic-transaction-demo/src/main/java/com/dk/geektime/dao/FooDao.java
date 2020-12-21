package com.dk.geektime.dao;

import com.dk.geektime.dao.mapper.FooRowMapper;
import com.dk.geektime.entity.Foo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.HashMap;
import java.util.List;

/**
 * @author DingKay
 */
@Repository
@Slf4j
public class FooDao {
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;
    private TransactionTemplate transactionTemplate;

    @Autowired
    private void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    private void setSimpleJdbcInsert(SimpleJdbcInsert simpleJdbcInsert) {
        this.simpleJdbcInsert = simpleJdbcInsert;
    }

    @Autowired
    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

    public void insertData() {
        jdbcTemplate.execute("INSERT INTO FOO (ID, BAR) VALUES (10, 'BAR-10')");

        HashMap<String, String> row = new HashMap<>(1);
        row.put("BAR", "BAR-SIMPLE-INSERT");
        Number number = simpleJdbcInsert.executeAndReturnKey(row);
        log.info("ID: {}", number.intValue());
    }

    public void listData() {
        log.info("Count:{}", getCount());
        List<Foo> list = jdbcTemplate.query("SELECT * FROM FOO", new FooRowMapper());
        list.forEach(row -> log.info("Foo Obj: {}", row));
        jdbcTemplate.query("SELECT * FROM FOO", (rs, i) -> Foo.builder()
                .id(rs.getInt(1))
                .bar(rs.getString(2))
                .build()).forEach(row -> log.info("Bar: {}", row.getBar()));
    }

    public void programmaticTransaction() {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                log.info("FOO COUNT:{} BEFORE TRANSACTION!", getCount());
                jdbcTemplate.execute("INSERT INTO FOO (ID, BAR) VALUES (100, 'BAR-TRANSACTION_TEST')");
                log.info("FOO COUNT:{} IN TRANSACTION!", getCount());
                transactionStatus.setRollbackOnly();
            }
        });
        log.info("FOO COUNT:{} AFTER TRANSACTION!", getCount());
    }

    private int getCount() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM FOO", Integer.class);
    }
}
