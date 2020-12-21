package com.dk.geektime;

import com.dk.geektime.dao.BatchFooDao;
import com.dk.geektime.dao.FooDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Kay
 */
@SpringBootApplication
public class SimpleJdbcDemo implements CommandLineRunner {
    private FooDao fooDao;
    private BatchFooDao batchFooDao;

    @Autowired
    public void setFooDao(FooDao fooDao) {
        this.fooDao = fooDao;
    }

    @Autowired
    public void setBatchFooDao(BatchFooDao batchFooDao) {
        this.batchFooDao = batchFooDao;
    }

    public static void main(String[] args) {
        SpringApplication.run(SimpleJdbcDemo.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        fooDao.insertData();
        batchFooDao.batchInsert();
        fooDao.listData();
    }
}
