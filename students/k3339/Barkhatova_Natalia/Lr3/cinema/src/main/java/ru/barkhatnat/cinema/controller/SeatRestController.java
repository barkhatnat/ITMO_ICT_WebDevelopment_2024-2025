package ru.barkhatnat.cinema.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.barkhatnat.cinema.domain.Seat;
import ru.barkhatnat.cinema.dto.create.SeatCreateDto;
import ru.barkhatnat.cinema.dto.regular.SeatDto;
import ru.barkhatnat.cinema.dto.update.SeatUpdateDto;
import ru.barkhatnat.cinema.service.SeatService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rest/seats")
@RequiredArgsConstructor
public class SeatRestController {

    private final SeatService seatService;

    @DeleteMapping
    public ResponseEntity<Void> deleteById(@RequestBody UUID id) {
        seatService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<SeatDto>> findAll() {
        return ResponseEntity.ok(seatService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeatDto> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(seatService.findById(id));
    }

    @PostMapping
    public ResponseEntity<SeatDto> create(@RequestBody @Valid SeatCreateDto seat) {
        return ResponseEntity.ok(seatService.create(seat));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SeatUpdateDto> update(@PathVariable UUID id, @RequestBody SeatUpdateDto seatUpdateDto) {
        return ResponseEntity.ok(seatService.update(id, seatUpdateDto));
    }
}
