package com.byultudy.redisstudy.common.exception;

public class RedisException extends RuntimeException {
    public RedisException(final String message, final Throwable throwable) {
        super(message, throwable);
    }
}
