package com.dk.geektime.dao;

import com.dk.geektime.entity.Foo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kay
 */
@Repository
@Slf4j
public class BatchFooDao {
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public void batchInsert() {
        jdbcTemplate.batchUpdate("INSERT INTO FOO (BAR) VALUES (?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setString(1, "batch-" + i);
            }

            @Override
            public int getBatchSize() {
                return 2;
            }
        });

        List<Foo> list = new ArrayList<>();
        list.add(Foo.builder().id(200L).bar("batch-200").build());
        list.add(Foo.builder().id(201L).bar("batch-201").build());
        namedParameterJdbcTemplate.batchUpdate("INSERT INTO FOO (ID, BAR) VALUES (:id, :bar)",
                SqlParameterSourceUtils.createBatch(list));
    }
}
