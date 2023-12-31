package com.byultudy.redisstudy.concert;


import com.byultudy.redisstudy.common.exception.ConcertAlreadyEndedException;
import com.byultudy.redisstudy.common.exception.TicketAlreadyOwnedException;
import com.byultudy.redisstudy.common.exception.TicketNotRemainedException;
import com.byultudy.redisstudy.common.redis.RedisLockService;
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
public class ConcertServiceImpl implements ConcertService {
    private static final String REDIS_LOCK_PREFIX = "concert_purchase_id:";
    private final ConcertCacheService concertCacheService;
    private final TicketService ticketService;
    private final CustomerService customerService;
    private final RedisLockService redisLockService;


    @Override
    @Transactional
    public TicketPurchaseResultDto purchaseTicket(final TicketPurchaseRequestDto ticketPurchaseRequestDto) {
        return redisLockService.tryWithLock(this.generateLockKey(ticketPurchaseRequestDto), () -> {
            Concert concert = concertCacheService.getConcert(ticketPurchaseRequestDto.getConcertId());

            LocalDateTime now = LocalDateTime.now();
            this.validConcert(concert, ticketPurchaseRequestDto.getCustomerId(), now);

            TicketDto ticketDto = ticketService.issueTicket(ticketPurchaseRequestDto, now, concert.isVipTicketRemained());
            CustomerDto customerDto = customerService.buyTicket(ticketDto);
            concert.sellTicket();

            concertCacheService.saveConcertAfterCommit(concert);
            return TicketPurchaseResultDto.from(concert, ticketDto, customerDto);
        });
    }

    private String generateLockKey(final TicketPurchaseRequestDto ticketPurchaseRequestDto) {
        return REDIS_LOCK_PREFIX + ticketPurchaseRequestDto.getConcertId();
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
}
