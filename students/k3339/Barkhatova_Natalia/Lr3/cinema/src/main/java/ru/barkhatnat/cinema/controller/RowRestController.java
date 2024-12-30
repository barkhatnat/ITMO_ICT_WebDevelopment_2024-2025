package ru.barkhatnat.cinema.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.barkhatnat.cinema.domain.Row;
import ru.barkhatnat.cinema.dto.create.RowCreateDto;
import ru.barkhatnat.cinema.dto.regular.RowDto;
import ru.barkhatnat.cinema.dto.update.RoleUpdateDto;
import ru.barkhatnat.cinema.dto.update.RowUpdateDto;
import ru.barkhatnat.cinema.mapper.RowMapper;
import ru.barkhatnat.cinema.repository.RowRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/rest/rows")
@RequiredArgsConstructor
public class RowRestController {

    private final RowRepository rowRepository;

    private final RowMapper rowMapper;

    private final ObjectMapper objectMapper;

    @DeleteMapping
    public ResponseEntity<Void> deleteById(@RequestBody UUID id) {
        rowRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<RowDto>> findAll() {
        List<Row> rows = rowRepository.findAll();
        List<RowDto> rowDtos = rows.stream()
                .map(rowMapper::toRowDto)
                .toList();
        return ResponseEntity.ok(rowDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RowDto> findById(@PathVariable UUID id) {
        Optional<Row> rowOptional = rowRepository.findById(id);
        RowDto rowDto = rowMapper.toRowDto(rowOptional.orElse(null));
        return ResponseEntity.ok(rowDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RowUpdateDto> update(@PathVariable UUID id, @RequestBody RowUpdateDto rowUpdateDto) {
        Row row = rowRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));
        rowMapper.updateWithNull(rowUpdateDto, row);
        Row resultRow = rowRepository.save(row);
        return ResponseEntity.ok(rowMapper.toRowUpdateDto(resultRow));
    }

    @PostMapping
    public ResponseEntity<RowDto> create(@RequestBody @Valid RowCreateDto rowCreateDto) {
        Row row = rowMapper.toEntity(rowCreateDto);
        Row resultRow = rowRepository.save(row);
        RowDto rowDto = rowMapper.toRowDto(resultRow);
        return ResponseEntity.ok(rowDto);
    }
}

