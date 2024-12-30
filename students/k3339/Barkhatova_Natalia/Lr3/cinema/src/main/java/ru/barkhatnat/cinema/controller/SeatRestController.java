package ru.barkhatnat.cinema.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.barkhatnat.cinema.domain.Seat;
import ru.barkhatnat.cinema.dto.regular.SeatDto;
import ru.barkhatnat.cinema.dto.update.SeatUpdateDto;
import ru.barkhatnat.cinema.mapper.SeatMapper;
import ru.barkhatnat.cinema.repository.SeatRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/rest/seats")
@RequiredArgsConstructor
public class SeatRestController {

    private final SeatRepository seatRepository;

    private final SeatMapper seatMapper;

    private final ObjectMapper objectMapper;

    @DeleteMapping
    public ResponseEntity<Void> deleteById(@RequestBody UUID id) {
        seatRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<SeatDto>> findAll() {
        List<Seat> seats = seatRepository.findAll();
        List<SeatDto> seatDtos = seats.stream()
                .map(seatMapper::toSeatDto)
                .toList();
        return ResponseEntity.ok(seatDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeatDto> findById(@PathVariable UUID id) {
        Optional<Seat> seatOptional = seatRepository.findById(id);
        SeatDto seatDto = seatMapper.toSeatDto(seatOptional.orElse(null));
        return ResponseEntity.ok(seatDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SeatUpdateDto> update(@PathVariable UUID id, @RequestBody SeatUpdateDto seatUpdateDto) {
        Seat seat = seatRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));
        seatMapper.updateWithNull(seatUpdateDto, seat);
        Seat resultSeat = seatRepository.save(seat);
        return ResponseEntity.ok(seatMapper.toSeatUpdateDto(resultSeat));
    }

    @PostMapping
    public ResponseEntity<SeatDto> create(@RequestBody @Valid Seat entity) {
        Seat seat = seatRepository.save(entity);
        SeatDto seatDto = seatMapper.toSeatDto(seat);
        return ResponseEntity.ok(seatDto);
    }
}

