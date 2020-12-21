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
 * @author DingKay
 */
@Slf4j
@Repository
public class BatchFooDao {
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    private void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public void batchInsertData() {
        jdbcTemplate.batchUpdate("INSERT INTO FOO (ID, BAR) VALUES (?, ?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setInt(1, 20 + i);
                preparedStatement.setString(2, "BAR-BATCH" + i);
            }

            @Override
            public int getBatchSize() {
                return 5;
            }
        });

        List<Foo> list = new ArrayList<>();
        list.add(Foo.builder().id(30).bar("BAR-30-NAMED_BATCH").build());
        list.add(Foo.builder().id(31).bar("BAR-31-NAMED_BATCH").build());
        namedParameterJdbcTemplate.batchUpdate("INSERT INTO FOO (ID, BAR) VALUES (:id, :bar)",
                SqlParameterSourceUtils.createBatch(list));
    }
}
