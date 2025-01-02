package ru.barkhatnat.cinema.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.barkhatnat.cinema.dto.create.TicketCreateDto;
import ru.barkhatnat.cinema.dto.regular.TicketDto;
import ru.barkhatnat.cinema.dto.update.TicketUpdateDto;
import ru.barkhatnat.cinema.service.TicketService;
import ru.barkhatnat.cinema.util.SecurityUtil;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rest")
@RequiredArgsConstructor
public class TicketRestController {

    private final TicketService ticketService;
    private final SecurityUtil securityUtil;

    @DeleteMapping("/admin/tickets")
    public ResponseEntity<Void> deleteById(@RequestBody UUID id) {
        ticketService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/admin/tickets/{id}")
    public ResponseEntity<TicketUpdateDto> patch(@PathVariable UUID id, @RequestBody TicketUpdateDto ticketUpdateDto) {
        return ResponseEntity.ok(ticketService.update(id, ticketUpdateDto));
    }

    @GetMapping("/admin/tickets")
    public ResponseEntity<List<TicketDto>> findAll() {
        return ResponseEntity.ok(ticketService.findAll());
    }

    @GetMapping("/tickets/user")
    public ResponseEntity<List<TicketDto>> findAllCurrentUserTickets() {
        UUID currentUserId = securityUtil.getCurrentUserDetails().getUser().getId();
        return ResponseEntity.ok(ticketService.findAll(currentUserId));
    }

    @GetMapping("/tickets/{id}")
    public ResponseEntity<TicketDto> findById(@PathVariable UUID id) {
        UUID currentUserId = securityUtil.getCurrentUserDetails().getUser().getId();
        return ResponseEntity.ok(ticketService.findById(id, currentUserId));
    }

    @PostMapping("/tickets")
    public ResponseEntity<TicketDto> create(@RequestBody @Valid TicketCreateDto ticketCreateDto) {
        return ResponseEntity.ok(ticketService.book(ticketCreateDto));
    }
}
