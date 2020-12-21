package com.dk.geektime.dao.mapper;


import com.dk.geektime.entity.Foo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author DingKay
 */
public class FooRowMapper implements RowMapper<Foo> {
    @Override
    public Foo mapRow(ResultSet resultSet, int i) throws SQLException {
        return Foo.builder().id(resultSet.getInt("ID")).bar(resultSet.getString("BAR")).build();
    }
}
