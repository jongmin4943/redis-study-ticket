package com.byultudy.redisstudy.customer;

import com.byultudy.redisstudy.common.exception.CustomerNotExistException;
import com.byultudy.redisstudy.ticket.TicketDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerDto buyTicket(final TicketDto ticketDto) {
        Customer customer = customerRepository.findById(ticketDto.getCustomerId())
                .orElseThrow(() -> new CustomerNotExistException(ticketDto.getCustomerId()));
        customer.receiveTicket(ticketDto.getTicketId());
        return CustomerDto.from(customer);
    }
}
