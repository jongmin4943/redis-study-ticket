package com.byultudy.redisstudy.common.redis;

import java.util.function.Supplier;

public interface RedisLockService {
    <T> T tryWithLock(String lockKey, Supplier<T> runnable, RedisLockDto lockDto);

    default <T> T tryWithLock(String lockKey, Supplier<T> runnable) {
        return this.tryWithLock(lockKey, runnable, RedisLockDto.createDefault());
    }

}
