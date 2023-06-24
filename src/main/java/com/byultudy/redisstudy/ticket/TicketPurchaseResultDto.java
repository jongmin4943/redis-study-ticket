package com.byultudy.redisstudy.ticket;


import com.byultudy.redisstudy.concert.Concert;
import com.byultudy.redisstudy.customer.CustomerDto;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class TicketPurchaseResultDto {
    private Long ticketId;
    private Long customerId;
    private String customerName;
    private Long concertId;
    private LocalDateTime concertDateTime;
    private LocalDateTime ticketIssueAt;

    public static TicketPurchaseResultDto from(final Concert concert, final TicketDto ticket, final CustomerDto customer) {
        return TicketPurchaseResultDto.builder()
                .concertId(concert.getId())
                .concertDateTime(concert.getTargetDateTime())
                .ticketId(ticket.getTicketId())
                .ticketIssueAt(ticket.getIssueAt())
                .customerId(customer.getId())
                .customerName(customer.getName())
                .build();
    }

    public static TicketPurchaseResultDto from(final Concert concert, final Long customerId) {
        return TicketPurchaseResultDto.builder()
                .concertId(concert.getId())
                .concertDateTime(concert.getTargetDateTime())
                .customerId(customerId)
                .build();
    }
}
