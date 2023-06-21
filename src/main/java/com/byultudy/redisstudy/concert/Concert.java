package com.byultudy.redisstudy.concert;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "concert")
@SequenceGenerator(
        name = "CONCERT_SEQ_GEN",
        sequenceName = "SEQ_CONCERT",
        allocationSize = 1
)
public class Concert {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONCERT_SEQ_GEN")
    @Column(name = "concert_id")
    private Long id;

    private Long ticketQuantity;

    private LocalDateTime targetDateTime;

    public boolean hasRemainedTicket() {
        return this.ticketQuantity.compareTo(0L) > 0;
    }

    public boolean isEnded(final LocalDateTime time) {
        return time.isAfter(this.targetDateTime);
    }

    public void sellTicket() {
        this.ticketQuantity = this.ticketQuantity - 1;
    }
}