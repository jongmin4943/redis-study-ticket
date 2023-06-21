package com.byultudy.redisstudy.customer;

import com.byultudy.redisstudy.common.exception.TicketAlreadyOwnedException;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "customer")
@SequenceGenerator(
        name = "CUSTOMER_SEQ_GEN",
        sequenceName = "SEQ_CUSTOMER",
        allocationSize = 1
)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUSTOMER_SEQ_GEN")
    @Column(name = "customer_id")
    private Long id;

    private String name;
    private Long ticketId;

    public void receiveTicket(final Long ticketId) {
        if (this.hasTicket()) {
            throw new TicketAlreadyOwnedException(this.id);
        }
        this.ticketId = ticketId;
    }

    public boolean hasTicket() {
        return this.ticketId != null;
    }
}