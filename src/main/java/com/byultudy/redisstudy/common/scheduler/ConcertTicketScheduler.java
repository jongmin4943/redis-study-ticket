package com.byultudy.redisstudy.common.scheduler;

import com.byultudy.redisstudy.concert.ConcertServiceWithZSet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import static com.byultudy.redisstudy.concert.ConcertServiceWithZSet.REDIS_Z_SET_PREFIX;

@Component
@Slf4j
@RequiredArgsConstructor
public class ConcertTicketScheduler {
    private final RedisTemplate<String, String> redisTemplate;
    private final ConcertServiceWithZSet concertService;
    private final ThreadLocal<Boolean> isFirstCall = ThreadLocal.withInitial(() -> true);
    private static final Long MAXIMUM_PROCESS_NUMBER = 10L;


    @Scheduled(fixedDelay = 1000)
    @Transactional
    public void processConcertScheduler() {
        Cursor<String> scan = redisTemplate.scan(ScanOptions.scanOptions().count(10).match(REDIS_Z_SET_PREFIX + "*").build());
        try (scan) {
            while (scan.hasNext()) {
                Long concertId = extractConcertId(scan);
                // 갯수
                Long count = redisTemplate.opsForZSet().zCard(REDIS_Z_SET_PREFIX + concertId);
                if (!ObjectUtils.isEmpty(count) && count.compareTo(0L) > 0) {
                    concertService.sellTicketInOrder(concertId, isFirstCall.get(), MAXIMUM_PROCESS_NUMBER);
                    isFirstCall.set(false);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private Long extractConcertId(Cursor<String> keys) {
        String key = keys.next();
        int index = key.indexOf(":");
        return Long.valueOf(key.substring(index + 1));
    }
}
