package com.byultudy.redisstudy.ticket;

import lombok.Data;

@Data
public class TicketPurchaseRequestDto {
    private Long customerId;
    private Long concertId;

    public static TicketPurchaseRequestDto of(final Long customerId, final Long concertId) {
        TicketPurchaseRequestDto result = new TicketPurchaseRequestDto();
        result.setCustomerId(customerId);
        result.setConcertId(concertId);
        return result;
    }
}
