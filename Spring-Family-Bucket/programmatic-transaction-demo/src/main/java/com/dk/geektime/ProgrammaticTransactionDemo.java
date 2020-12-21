package com.dk.geektime;

import com.dk.geektime.dao.BatchFooDao;
import com.dk.geektime.dao.FooDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author DingKay
 */
@SpringBootApplication
@Slf4j
public class ProgrammaticTransactionDemo implements CommandLineRunner {
    private FooDao fooDao;
    private BatchFooDao batchFooDao;

    @Autowired
    private void setFooDao(FooDao fooDao) {
        this.fooDao = fooDao;
    }

    @Autowired
    private void setBatchFooDao(BatchFooDao batchFooDao) {
        this.batchFooDao = batchFooDao;
    }

    public static void main(String[] args) {
        SpringApplication.run(ProgrammaticTransactionDemo.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        fooDao.insertData();
        fooDao.programmaticTransaction();
        batchFooDao.batchInsertData();
        fooDao.listData();
    }
}
