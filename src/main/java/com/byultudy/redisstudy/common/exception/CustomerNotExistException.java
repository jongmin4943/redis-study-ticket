package com.byultudy.redisstudy.common.exception;

public class CustomerNotExistException extends RuntimeException {
    public CustomerNotExistException(final Long id) {
        super("존재하지 않는 손님입니다. id : " + id);
    }
}
