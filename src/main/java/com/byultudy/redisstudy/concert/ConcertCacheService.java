package com.byultudy.redisstudy.concert;public interface ConcertCacheService {
    void saveConcert(Concert concert);

    Concert getConcert(Long concertId);

    void saveConcertAfterCommit(Concert concert);
}
