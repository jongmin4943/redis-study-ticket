package com.byultudy.redisstudy.concert;

import com.byultudy.redisstudy.ticket.TicketPurchaseRequestDto;
import com.byultudy.redisstudy.ticket.TicketPurchaseResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/concert")
@RequiredArgsConstructor
public class ConcertController {

    private final ConcertService concertService;

    @PostMapping("/purchase/ticket")
    public ResponseEntity<TicketPurchaseResultDto> buyTicket(@RequestBody TicketPurchaseRequestDto requestDto) {
        return ResponseEntity.ok(concertService.purchaseTicket(requestDto));
    }
}
