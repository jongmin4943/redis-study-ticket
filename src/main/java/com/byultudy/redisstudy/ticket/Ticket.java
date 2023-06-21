package com.byultudy.redisstudy.ticket;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Getter
@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "ticket")
@SequenceGenerator(
        name = "TICKET_SEQ_GEN",
        sequenceName = "SEQ_TICKET",
        allocationSize = 1
)
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TICKET_SEQ_GEN")
    @Column(name = "ticket_id")
    private Long id;

    private Long customerId;

    private Long concertId;

    private LocalDateTime issueAt;

    private Boolean isVip;

    public static Ticket issue(final TicketPurchaseRequestDto ticketPurchaseRequestDto, final LocalDateTime now, final Boolean vipTicket) {
        return Ticket.builder()
                .customerId(ticketPurchaseRequestDto.getCustomerId())
                .concertId(ticketPurchaseRequestDto.getConcertId())
                .issueAt(now)
                .isVip(vipTicket)
                .build();
    }
}