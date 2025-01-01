package ru.barkhatnat.cinema.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.barkhatnat.cinema.domain.Ticket;
import ru.barkhatnat.cinema.domain.enums.RoleName;
import ru.barkhatnat.cinema.dto.create.TicketCreateDto;
import ru.barkhatnat.cinema.dto.regular.TicketDto;
import ru.barkhatnat.cinema.dto.update.TicketUpdateDto;
import ru.barkhatnat.cinema.exception.ForbiddenException;
import ru.barkhatnat.cinema.mapper.TicketMapper;
import ru.barkhatnat.cinema.repository.SeatRepository;
import ru.barkhatnat.cinema.repository.SessionRepository;
import ru.barkhatnat.cinema.repository.TicketRepository;
import ru.barkhatnat.cinema.repository.UserRepository;

import java.util.List;
import java.util.Optional;
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
        ticketMapper.updateWithNull(ticketUpdateDto, ticket, userRepository, sessionRepository, seatRepository);
        Ticket updatedTicket = ticketRepository.save(ticket);
        return ticketMapper.toTicketUpdateDto(updatedTicket);
    }

    public List<TicketDto> findAll() {
        List<Ticket> tickets = ticketRepository.findAll();
        return tickets.stream()
                .map(ticketMapper::toTicketDto)
                .toList();
    }

    public List<TicketDto> findAll(UUID userId) {
        List<Ticket> tickets = ticketRepository.findByUserId(userId);
        return tickets.stream()
                .map(ticketMapper::toTicketDto)
                .toList();
    }

    public TicketDto findById(UUID ticketId, UUID userId) {
        checkTicketOwnership(ticketId, userId);
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(ticketId)));
        return ticketMapper.toTicketDto(ticket);
    }

    public TicketDto create(@Valid TicketCreateDto ticketCreateDto) {
        Ticket ticket = ticketMapper.toEntity(ticketCreateDto, userRepository, sessionRepository, seatRepository);
        Ticket savedTicket = ticketRepository.save(ticket);
        return ticketMapper.toTicketDto(savedTicket);
    }

    private void checkTicketOwnership(UUID ticketId, UUID userId) {
        RoleName userRoleName = userRepository.findById(userId).get().getRole().getName();
        Optional<Ticket> ticket = ticketRepository.findById(ticketId);
        if (ticket.isEmpty()
            || ticket.get().getUser() == null
            || (!ticket.get().getUser().getId().equals(userId)
            && userRoleName.equals(RoleName.USER))) {
            throw new ForbiddenException("Access denied");
        }
    }
}
