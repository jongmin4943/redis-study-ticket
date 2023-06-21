package com.byultudy.redisstudy.common.exception;

public class ConcertAlreadyEndedException extends RuntimeException {
    public ConcertAlreadyEndedException() {
        super("이미 진행된 콘서트입니다.");
    }
}
