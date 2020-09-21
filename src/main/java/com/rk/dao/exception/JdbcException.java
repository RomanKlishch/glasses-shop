package com.rk.dao.exception;

public class JdbcException extends RuntimeException{
    public JdbcException(String message, Throwable cause) {
        super(message, cause);
    }

    public JdbcException(String message) {
        super(message);
    }
}
