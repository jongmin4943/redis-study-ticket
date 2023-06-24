package com.byultudy.redisstudy.concert;

import com.byultudy.redisstudy.ticket.TicketPurchaseRequestDto;
import com.byultudy.redisstudy.ticket.TicketPurchaseResultDto;
import org.springframework.transaction.annotation.Transactional;

public interface ConcertService {
    @Transactional
    TicketPurchaseResultDto purchaseTicket(TicketPurchaseRequestDto ticketPurchaseRequestDto);
}
