package com.byultudy.redisstudy.concert;


import com.byultudy.redisstudy.common.exception.ConcertNotExistException;
import com.byultudy.redisstudy.common.exception.RedisException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConcertCacheServiceImpl implements ConcertCacheService {
    private static final String REDIS_CONCERT_TICKET_QUANTITY_PREFIX = "concert_ticket_quantity_id:";
    private final ConcertRepository concertRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void saveConcert(final Concert concert) {
        try {
            redisTemplate.opsForValue().set(this.generateKey(concert.getId()), objectMapper.writeValueAsString(concert), Duration.of(30, ChronoUnit.SECONDS));
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new RedisException("Redis save 파싱중 에러", e);
        }
    }

    @Override
    public Concert getConcert(final Long concertId) {
        String cachedConcert = redisTemplate.opsForValue().get(this.generateKey(concertId));

        if (cachedConcert == null) {
            Concert concert = concertRepository.findById(concertId)
                    .orElseThrow(() -> new ConcertNotExistException(concertId));
            this.saveConcert(concert);
            return concert;
        }

        try {
            return objectMapper.readValue(cachedConcert, Concert.class);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new RedisException("Redis save 파싱중 에러", e);
        }
    }

    private String generateKey(final Long concertId) {
        return REDIS_CONCERT_TICKET_QUANTITY_PREFIX + concertId;
    }

    @Override
    public void saveConcertAfterCommit(final Concert concert) {
        concertRepository.updateConcertTicketQuantity(concert.getId(), concert.getTicketQuantity());
        applicationEventPublisher.publishEvent(concert);
    }

    @TransactionalEventListener
    public void subscribeSaveConcert(final Concert concert) {
        this.saveConcert(concert);
    }

}
