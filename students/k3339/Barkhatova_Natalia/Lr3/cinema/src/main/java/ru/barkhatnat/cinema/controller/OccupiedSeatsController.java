package ru.barkhatnat.cinema.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.barkhatnat.cinema.service.OccupiedSeatsService;

import java.util.UUID;

@RestController
@RequestMapping("/rest/seats")
@RequiredArgsConstructor
public class OccupiedSeatsController {

    private final OccupiedSeatsService occupiedSeatsService;

    @GetMapping("/{sessionId}/{seatId}/status")
    public ResponseEntity<Boolean> isSeatOccupied(
            @PathVariable UUID sessionId,
            @PathVariable UUID seatId) {
        boolean isOccupied = occupiedSeatsService.isSeatOccupied(sessionId, seatId);
        return ResponseEntity.ok(isOccupied);
    }

    @PostMapping("/{sessionId}/{seatId}/occupy")
    public ResponseEntity<Void> occupySeat(
            @PathVariable UUID sessionId,
            @PathVariable UUID seatId) {
        occupiedSeatsService.markSeatAsOccupied(sessionId, seatId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{sessionId}/{seatId}/free")
    public ResponseEntity<Void> freeSeat(
            @PathVariable UUID sessionId,
            @PathVariable UUID seatId) {
        occupiedSeatsService.markSeatAsFree(sessionId, seatId);
        return ResponseEntity.ok().build();
    }
}
