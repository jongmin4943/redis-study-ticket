package com.byultudy.redisstudy.common.exception;

public class TicketNotRemainedException extends RuntimeException {
    public TicketNotRemainedException() {
        super("남은 티켓이 없습니다.");
    }
}
