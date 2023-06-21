package com.byultudy.redisstudy.common.exception;

public class TicketAlreadyOwnedException extends RuntimeException {
    public TicketAlreadyOwnedException(final Long customerId) {
        super(customerId + "님은 이미 해당 콘서트티켓을 구매하셨습니다.");
    }
}
