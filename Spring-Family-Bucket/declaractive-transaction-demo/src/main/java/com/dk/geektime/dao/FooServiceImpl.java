package com.dk.geektime.dao;

import com.dk.geektime.exception.RollbackException;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Kay
 */
@Service
public class FooServiceImpl implements FooService {
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

    @Override
    public void insertDataWithoutTransaction() {
        jdbcTemplate.update("INSERT INTO FOO (BAR) VALUES ('AAA')");
    }

    @Transactional
    @Override
    public void insertDataTransaction() {
        jdbcTemplate.update("INSERT INTO FOO (BAR) VALUES ('BBB')");
    }

    @Transactional(rollbackFor = RollbackException.class)
    @Override
    public void insertDataWhenExceptionRollback() throws RollbackException {
        jdbcTemplate.update("INSERT INTO FOO (BAR) VALUES ('CCC')");
        throw new RollbackException();
    }

    @Transactional(rollbackFor = RollbackException.class)
    @Override
    public void insertData() throws RollbackException {
        jdbcTemplate.update("INSERT INTO FOO (BAR) VALUES ('DDD')");
        throw new RollbackException();
    }

    @Override
    public void insertDataWithoutTransactionByInvokeOtherMethod() throws RollbackException {
        insertData();
    }

    @Override
    public void insertDataWithoutTransactionBySelfInvokeMethod() throws RollbackException {
        fooService.insertData();
    }

    @Override
    public void insertDataByProxyObjInvoke() throws RollbackException {
        ((FooService)AopContext.currentProxy()).insertData();
    }

    @Transactional(rollbackFor = RollbackException.class)
    @Override
    public void insertDataWithTransaction() throws RollbackException {
        insertData();
    }
}
