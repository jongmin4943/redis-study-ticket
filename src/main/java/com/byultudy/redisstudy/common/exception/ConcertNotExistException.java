package com.byultudy.redisstudy.common.exception;

public class ConcertNotExistException extends RuntimeException {
    public ConcertNotExistException(final Long id) {
        super("존재하지 않는 콘서트입니다. id : " + id);
    }
}
