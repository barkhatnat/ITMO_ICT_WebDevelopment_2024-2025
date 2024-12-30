package ru.barkhatnat.cinema.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.barkhatnat.cinema.domain.Ticket;
import ru.barkhatnat.cinema.dto.create.TicketCreateDto;
import ru.barkhatnat.cinema.dto.regular.TicketDto;
import ru.barkhatnat.cinema.dto.update.TicketUpdateDto;
import ru.barkhatnat.cinema.mapper.TicketMapper;
import ru.barkhatnat.cinema.repository.TicketRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/rest/tickets")
@RequiredArgsConstructor
public class TicketRestController {

    private final TicketRepository ticketRepository;

    private final TicketMapper ticketMapper;

    private final ObjectMapper objectMapper;

    @DeleteMapping
    public ResponseEntity<Void> deleteById(@RequestBody UUID id) {
        ticketRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketUpdateDto> patch(@PathVariable UUID id, @RequestBody TicketUpdateDto ticketUpdateDto) {
        Ticket ticket = ticketRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));
        ticketMapper.updateWithNull(ticketUpdateDto, ticket);
        Ticket resultTicket = ticketRepository.save(ticket);
        return ResponseEntity.ok(ticketMapper.toTicketUpdateDto(resultTicket));
    }

    @GetMapping
    public ResponseEntity<List<TicketDto>> findAll() {
        List<Ticket> tickets = ticketRepository.findAll();
        List<TicketDto> ticketDtos = tickets.stream()
                .map(ticketMapper::toTicketDto)
                .toList();
        return ResponseEntity.ok(ticketDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketDto> findById(@PathVariable UUID id) {
        Optional<Ticket> ticketOptional = ticketRepository.findById(id);
        TicketDto ticketDto = ticketMapper.toTicketDto(ticketOptional.orElse(null));
        return ResponseEntity.ok(ticketDto);
    }

    @PostMapping
    public ResponseEntity<TicketDto> create(@RequestBody @Valid TicketCreateDto ticketCreateDto) {
        Ticket ticket = ticketMapper.toEntity(ticketCreateDto);
        Ticket resultTicket = ticketRepository.save(ticket);
        TicketDto ticketDto = ticketMapper.toTicketDto(resultTicket);
        return ResponseEntity.ok(ticketDto);
    }
}

