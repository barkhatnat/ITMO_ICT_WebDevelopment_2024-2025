package ru.barkhatnat.cinema.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.barkhatnat.cinema.domain.Session;
import ru.barkhatnat.cinema.dto.create.SessionCreateDto;
import ru.barkhatnat.cinema.dto.regular.SessionDto;
import ru.barkhatnat.cinema.dto.update.SessionUpdateDto;
import ru.barkhatnat.cinema.mapper.SessionMapper;
import ru.barkhatnat.cinema.repository.SessionRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/rest/sessions")
@RequiredArgsConstructor
public class SessionRestController {

    private final SessionRepository sessionRepository;

    private final SessionMapper sessionMapper;

    private final ObjectMapper objectMapper;

    @DeleteMapping
    public ResponseEntity<Void> deleteById(@RequestBody UUID id) {
        sessionRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<SessionUpdateDto> update(@PathVariable UUID id, @RequestBody SessionUpdateDto sessionUpdateDto)  {
        Session session = sessionRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));
        sessionMapper.updateWithNull(sessionUpdateDto, session);
        Session resultSession = sessionRepository.save(session);
        return ResponseEntity.ok(sessionMapper.toSessionUpdateDto(resultSession));
    }

    @GetMapping
    public ResponseEntity<List<SessionDto>> findAll() {
        List<Session> sessions = sessionRepository.findAll();
        List<SessionDto> sessionDtos = sessions.stream()
                .map(sessionMapper::toSessionDto)
                .toList();
        return ResponseEntity.ok(sessionDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessionDto> findById(@PathVariable UUID id) {
        Optional<Session> sessionOptional = sessionRepository.findById(id);
        SessionDto sessionDto = sessionMapper.toSessionDto(sessionOptional.orElse(null));
        return ResponseEntity.ok(sessionDto);
    }

    @PostMapping
    public ResponseEntity<SessionCreateDto> create(@RequestBody @Valid SessionDto sessionDto) {
        Session session = sessionMapper.toEntity(sessionDto);
        Session resultSession = sessionRepository.save(session);
        SessionCreateDto sessionCreateDto = sessionMapper.toSessionCreateDto(resultSession);
        return ResponseEntity.ok(sessionCreateDto);
    }
}

