package ru.barkhatnat.cinema.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.barkhatnat.cinema.dto.create.SessionCreateDto;
import ru.barkhatnat.cinema.dto.regular.SessionDto;
import ru.barkhatnat.cinema.dto.update.SessionUpdateDto;
import ru.barkhatnat.cinema.service.SessionService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rest/sessions")
@RequiredArgsConstructor
public class SessionRestController {

    private final SessionService sessionService;

    @DeleteMapping
    public ResponseEntity<Void> deleteById(@RequestBody UUID id) {
        sessionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<SessionUpdateDto> update(@PathVariable UUID id, @RequestBody SessionUpdateDto sessionUpdateDto) {
        return ResponseEntity.ok(sessionService.update(id, sessionUpdateDto));
    }

    @GetMapping
    public ResponseEntity<List<SessionDto>> findAll() {
        return ResponseEntity.ok(sessionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessionDto> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(sessionService.findById(id));
    }

    @PostMapping
    public ResponseEntity<SessionDto> create(@RequestBody @Valid SessionCreateDto sessionDto) {
        return ResponseEntity.ok(sessionService.create(sessionDto));
    }
}
