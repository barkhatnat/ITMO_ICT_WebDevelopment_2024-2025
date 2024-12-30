package ru.barkhatnat.cinema.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.barkhatnat.cinema.domain.Hall;
import ru.barkhatnat.cinema.dto.create.HallCreateDto;
import ru.barkhatnat.cinema.dto.regular.HallDto;
import ru.barkhatnat.cinema.dto.update.HallUpdateDto;
import ru.barkhatnat.cinema.mapper.HallMapper;
import ru.barkhatnat.cinema.repository.HallRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/rest/halls")
@RequiredArgsConstructor
public class HallRestController {

    private final HallRepository hallRepository;

    private final HallMapper hallMapper;

    private final ObjectMapper objectMapper;

    @DeleteMapping
    public ResponseEntity<Void> deleteById(@RequestBody UUID id) {
        hallRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<HallDto>> findAll() {
        List<Hall> halls = hallRepository.findAll();
        List<HallDto> hallDtos = halls.stream()
                .map(hallMapper::toHallDto)
                .toList();
        return ResponseEntity.ok(hallDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HallDto> findById(@PathVariable UUID id) {
        Optional<Hall> hallOptional = hallRepository.findById(id);
        HallDto hallDto = hallMapper.toHallDto(hallOptional.orElse(null));
        return ResponseEntity.ok(hallDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HallUpdateDto> update(@PathVariable UUID id, @RequestBody HallUpdateDto hallUpdateDto) {
        Hall hall = hallRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));
        hallMapper.updateWithNull(hallUpdateDto, hall);
        Hall resultHall = hallRepository.save(hall);
        return ResponseEntity.ok(hallMapper.toHallUpdateDto(resultHall));
    }

    @PostMapping
    public ResponseEntity<HallDto> save(@RequestBody @Valid HallCreateDto hallCreateDto) {
        Hall hall = hallMapper.toEntity(hallCreateDto);
        Hall resultHall = hallRepository.save(hall);
        HallDto hallDto = hallMapper.toHallDto(resultHall);
        return ResponseEntity.ok(hallDto);
    }
}

