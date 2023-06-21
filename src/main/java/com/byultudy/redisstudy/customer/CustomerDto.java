package com.byultudy.redisstudy.customer;

import lombok.*;

@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CustomerDto {
    private Long id;
    private String name;
    private Long ticketId;

    public static CustomerDto from(final Customer customer) {
        return CustomerDto.builder()
                .id(customer.getId())
                .name(customer.getName())
                .ticketId(customer.getTicketId())
                .build();
    }
}
