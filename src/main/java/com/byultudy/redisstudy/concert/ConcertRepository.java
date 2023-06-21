package com.byultudy.redisstudy.concert;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ConcertRepository extends JpaRepository<Concert, Long> {

    @Query("select C.ticketQuantity from Concert C where C.id = :id")
    Long getTicketQuantity(Long id);

    @Modifying
    @Query("update Concert C set C.ticketQuantity = :ticketQuantity where C.id = :id")
    void updateConcertTicketQuantity(Long id, Long ticketQuantity);
}
