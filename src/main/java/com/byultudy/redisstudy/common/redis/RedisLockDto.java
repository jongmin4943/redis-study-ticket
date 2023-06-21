package com.byultudy.redisstudy.common.redis;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class RedisLockDto {
    @Builder.Default
    private final long waitTime = 2L;
    @Builder.Default
    private final long leaseTime = 3L;
    @Builder.Default
    private final TimeUnit timeUnit = TimeUnit.SECONDS;

    public static RedisLockDto createDefault() {
        return RedisLockDto.builder().build();
    }
}

