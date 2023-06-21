package com.byultudy.redisstudy.concert;


import com.byultudy.redisstudy.common.exception.ConcertNotExistException;
import com.byultudy.redisstudy.common.exception.ConcertAlreadyEndedException;
import com.byultudy.redisstudy.common.exception.TicketAlreadyOwnedException;
import com.byultudy.redisstudy.common.exception.TicketNotRemainedException;
import com.byultudy.redisstudy.customer.CustomerDto;
import com.byultudy.redisstudy.customer.CustomerService;
import com.byultudy.redisstudy.ticket.TicketDto;
import com.byultudy.redisstudy.ticket.TicketPurchaseRequestDto;
import com.byultudy.redisstudy.ticket.TicketPurchaseResultDto;
import com.byultudy.redisstudy.ticket.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ConcertService {
    private final ConcertRepository concertRepository;
    private final TicketService ticketService;
    private final CustomerService customerService;

    @Transactional
    public TicketPurchaseResultDto purchaseTicket(final TicketPurchaseRequestDto ticketPurchaseRequestDto) {
        Concert concert = this.getConcert(ticketPurchaseRequestDto.getConcertId());

        LocalDateTime now = LocalDateTime.now();
        this.validConcert(concert, ticketPurchaseRequestDto.getCustomerId(), now);

        TicketDto ticketDto = ticketService.issueTicket(ticketPurchaseRequestDto, now);
        CustomerDto customerDto = customerService.buyTicket(ticketDto);
        concert.sellTicket();

        return TicketPurchaseResultDto.from(concert, ticketDto, customerDto);
    }

    private void validConcert(final Concert concert, final Long customerId, final LocalDateTime now) {
        if (!concert.hasRemainedTicket()) {
            throw new TicketNotRemainedException();
        }
        if (concert.isEnded(now)) {
            throw new ConcertAlreadyEndedException();
        }
        if (customerService.hasTicket(customerId)) {
            throw new TicketAlreadyOwnedException(customerId);
        }
    }

    private Concert getConcert(final Long concertId) {
        return concertRepository.findById(concertId)
                .orElseThrow(() -> new ConcertNotExistException(concertId));
    }
}
