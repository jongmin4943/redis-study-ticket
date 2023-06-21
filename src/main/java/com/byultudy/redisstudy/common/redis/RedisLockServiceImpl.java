package com.byultudy.redisstudy.common.redis;

import com.byultudy.redisstudy.common.exception.RedisException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisLockServiceImpl implements RedisLockService {

    private static final String REDIS_LOCK_PREFIX = "redis_lock:";
    private final RedissonClient redissonClient;
    private final ApplicationEventPublisher applicationEventPublisher;


    @Override
    public <T> T tryWithLock(final String lockKey, final Supplier<T> supplier, final RedisLockDto lockDto) {
        String key = REDIS_LOCK_PREFIX + lockKey;
        final RLock lock = redissonClient.getLock(key);
        return this.execute(supplier, lockDto, key, lock);

    }

    private <T> T execute(final Supplier<T> supplier, final RedisLockDto lockDto, final String key, final RLock lock) {
        try {
            log.info("{} - lock 획득 시도", key);
            if (lock.tryLock(lockDto.getWaitTime(), lockDto.getLeaseTime(), lockDto.getTimeUnit())) {
                log.info("{} - lock 획득 성공", key);
                return supplier.get();
            }
            throw new InterruptedException();
        } catch (InterruptedException e) {
            log.error("{} - lock 획득 실패", key);
            throw new RedisException("요청이 너무 많아 처리에 실패했습니다. 재시도 해주세요.", e);
        } finally {
            applicationEventPublisher.publishEvent(RedisLockEvent.of(key, lock));
        }
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
    public void subscribeUnlock(final RedisLockEvent lockEvent) {
        log.info("{} lock 해제", lockEvent.key());
        lockEvent.unlock();
    }


    private record RedisLockEvent(String key, RLock lock) {

        public static RedisLockEvent of(final String key, final RLock lock) {
            return new RedisLockEvent(key, lock);
        }

        public void unlock() {
            this.lock.unlock();
        }
    }

}
