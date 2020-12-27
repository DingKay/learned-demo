package com.dk.jdbc.suport;

import org.springframework.dao.DuplicateKeyException;

/**
 * @author Kay
 */
public class CustomDuplicateSqlException extends DuplicateKeyException {
    public CustomDuplicateSqlException(String msg) {
        super(msg);
    }

    public CustomDuplicateSqlException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
