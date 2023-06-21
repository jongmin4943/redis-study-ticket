package com.byultudy.redisstudy.customer;

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
        this.ticketId = ticketId;
    }
}