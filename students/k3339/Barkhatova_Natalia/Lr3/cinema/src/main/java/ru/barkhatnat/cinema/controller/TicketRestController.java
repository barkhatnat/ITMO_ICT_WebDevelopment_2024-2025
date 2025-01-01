package ru.barkhatnat.cinema.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.barkhatnat.cinema.dto.create.TicketCreateDto;
import ru.barkhatnat.cinema.dto.regular.TicketDto;
import ru.barkhatnat.cinema.dto.update.TicketUpdateDto;
import ru.barkhatnat.cinema.service.TicketService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rest/tickets")
@RequiredArgsConstructor
public class TicketRestController {

    private final TicketService ticketService;

    @DeleteMapping
    public ResponseEntity<Void> deleteById(@RequestBody UUID id) {
        ticketService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketUpdateDto> patch(@PathVariable UUID id, @RequestBody TicketUpdateDto ticketUpdateDto) {
        return ResponseEntity.ok(ticketService.update(id, ticketUpdateDto));
    }

    @GetMapping
    public ResponseEntity<List<TicketDto>> findAll() {
        return ResponseEntity.ok(ticketService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketDto> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(ticketService.findById(id));
    }

    @PostMapping
    public ResponseEntity<TicketDto> create(@RequestBody @Valid TicketCreateDto ticketCreateDto) {
        return ResponseEntity.ok(ticketService.create(ticketCreateDto));
    }
}
