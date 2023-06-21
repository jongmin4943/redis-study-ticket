package com.byultudy.redisstudy.ticket;

import lombok.Data;

@Data
public class TicketPurchaseRequestDto {
    private Long customerId;
    private Long concertId;
}
