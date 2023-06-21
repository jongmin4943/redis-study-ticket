package com.byultudy.redisstudy.concert;

import com.byultudy.redisstudy.customer.Customer;
import com.byultudy.redisstudy.customer.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
class ConcertServiceTest {

    @Autowired
    ConcertRepository concertRepository;
    @Autowired
    CustomerRepository customerRepository;


    @BeforeEach
    void setUp() {
        concertRepository.deleteAll();
        customerRepository.deleteAll();

        Concert concert = Concert.builder()
                .targetDateTime(LocalDateTime.now().plusDays(1))
                .ticketQuantity(100L)
                .build();
        concertRepository.save(concert);

        List<Customer> customers = IntStream.range(0, 10000)
                .mapToObj(i ->
                        Customer.builder()
                                .name("test")
                                .build())
                .toList();
        customerRepository.saveAll(customers);
    }

    @Test
    @Rollback(false)
    void init() {

    }
}