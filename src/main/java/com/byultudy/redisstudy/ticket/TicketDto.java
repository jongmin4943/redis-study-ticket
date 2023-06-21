package com.byultudy.redisstudy.ticket;


import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class TicketDto {
    private Long ticketId;
    private Long customerId;
    private Long concertId;
    private LocalDateTime issueAt;

    public static TicketDto from(final Ticket ticket) {
        return TicketDto.builder()
                .ticketId(ticket.getId())
                .customerId(ticket.getCustomerId())
                .concertId(ticket.getConcertId())
                .issueAt(ticket.getIssueAt())
                .build();
    }
}
