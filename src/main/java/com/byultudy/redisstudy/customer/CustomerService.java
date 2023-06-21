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
        Customer customer = this.getCustomer(ticketDto.getCustomerId());
        customer.receiveTicket(ticketDto.getTicketId());
        return CustomerDto.from(customer);
    }

    public boolean hasTicket(final Long customerId) {
        Customer customer = this.getCustomer(customerId);
        return customer.hasTicket();
    }

    private Customer getCustomer(final Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotExistException(customerId));
    }
}
