package ru.barkhatnat.cinema.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.barkhatnat.cinema.domain.Ticket;
import ru.barkhatnat.cinema.dto.create.TicketCreateDto;
import ru.barkhatnat.cinema.dto.regular.TicketDto;
import ru.barkhatnat.cinema.dto.update.TicketUpdateDto;
import ru.barkhatnat.cinema.mapper.TicketMapper;
import ru.barkhatnat.cinema.repository.SeatRepository;
import ru.barkhatnat.cinema.repository.SessionRepository;
import ru.barkhatnat.cinema.repository.TicketRepository;
import ru.barkhatnat.cinema.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final SeatRepository seatRepository;

    public void deleteById(UUID id) {
        ticketRepository.deleteById(id);
    }

    public TicketUpdateDto update(UUID id, TicketUpdateDto ticketUpdateDto) {
        Ticket ticket = ticketRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));
        ticketMapper.updateWithNull(ticketUpdateDto, ticket);
        Ticket updatedTicket = ticketRepository.save(ticket);
        return ticketMapper.toTicketUpdateDto(updatedTicket);
    }

    public List<TicketDto> findAll() {
        List<Ticket> tickets = ticketRepository.findAll();
        return tickets.stream()
                .map(ticketMapper::toTicketDto)
                .toList();
    }

    public TicketDto findById(UUID id) {
        Ticket ticket = ticketRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));
        return ticketMapper.toTicketDto(ticket);
    }

    public TicketDto create(@Valid TicketCreateDto ticketCreateDto) {
        Ticket ticket = ticketMapper.toEntity(ticketCreateDto, userRepository, sessionRepository, seatRepository);
        Ticket savedTicket = ticketRepository.save(ticket);
        return ticketMapper.toTicketDto(savedTicket);
    }
}
