package com.byultudy.redisstudy.concert;


import com.byultudy.redisstudy.common.exception.ConcertAlreadyEndedException;
import com.byultudy.redisstudy.common.exception.ConcertNotExistException;
import com.byultudy.redisstudy.common.exception.TicketAlreadyOwnedException;
import com.byultudy.redisstudy.common.exception.TicketNotRemainedException;
import com.byultudy.redisstudy.customer.CustomerDto;
import com.byultudy.redisstudy.customer.CustomerService;
import com.byultudy.redisstudy.ticket.TicketDto;
import com.byultudy.redisstudy.ticket.TicketPurchaseRequestDto;
import com.byultudy.redisstudy.ticket.TicketPurchaseResultDto;
import com.byultudy.redisstudy.ticket.TicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
@Primary
public class ConcertServiceWithZSet implements ConcertService {
    public static final String REDIS_Z_SET_PREFIX = "concert_id:";
    private final ConcertRepository concertRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final TicketService ticketService;
    private final CustomerService customerService;

    @Override
    @Transactional
    public TicketPurchaseResultDto purchaseTicket(final TicketPurchaseRequestDto ticketPurchaseRequestDto) {
        Long concertId = ticketPurchaseRequestDto.getConcertId();
        Concert concert = this.getConcert(concertId);

        LocalDateTime now = LocalDateTime.now();
        Long customerId = ticketPurchaseRequestDto.getCustomerId();
        this.validConcert(concert, customerId, now);

        long score = now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        redisTemplate.opsForZSet().add(REDIS_Z_SET_PREFIX + concertId, String.valueOf(customerId), (int) score);
        log.info("{} 손님이 줄섰습니다. 시간 : {} : 점수 : {}", customerId, now, score);

        return TicketPurchaseResultDto.from(concert, customerId);
    }

    private Concert getConcert(final Long concertId) {
        return concertRepository.findById(concertId)
                .orElseThrow(() -> new ConcertNotExistException(concertId));
    }


    private void validConcert(final Concert concert, final Long customerId, final LocalDateTime now) {
        if (concert.isEnded(now)) {
            throw new ConcertAlreadyEndedException();
        }
        if (customerService.hasTicket(customerId)) {
            throw new TicketAlreadyOwnedException(customerId);
        }
    }

    public void sellTicketInOrder(final Long concertId, final Boolean isVip, final Long count) {
        Set<String> customerQueue = redisTemplate.opsForZSet().range(REDIS_Z_SET_PREFIX + concertId, 0, count - 1L);
        if (ObjectUtils.isEmpty(customerQueue)) {
            return;
        }
        Concert concert = this.getConcert(concertId);

        if (!concert.hasRemainedTicket()) {
            log.error("티켓이 매진되었습니다");
            // 전체 지우기
            redisTemplate.opsForZSet().removeRange(REDIS_Z_SET_PREFIX + concertId, 0, -1);
            throw new TicketNotRemainedException();
        }

        customerQueue.forEach(customerId->{
            TicketDto ticketDto = ticketService.issueTicket(TicketPurchaseRequestDto.of(Long.valueOf(customerId), concertId), LocalDateTime.now(), isVip);
            CustomerDto customerDto = customerService.buyTicket(ticketDto);
            concert.sellTicket();
            log.info("번호 : {}, 이름: {} 님에게 {} 콘서트 티켓이 발급되었습니다.", customerDto.getId(), customerDto.getName(), isVip ? "VIP" : "");
            // 특정 지우기
            redisTemplate.opsForZSet().remove(REDIS_Z_SET_PREFIX + concertId, customerId);
        });
    }

}
