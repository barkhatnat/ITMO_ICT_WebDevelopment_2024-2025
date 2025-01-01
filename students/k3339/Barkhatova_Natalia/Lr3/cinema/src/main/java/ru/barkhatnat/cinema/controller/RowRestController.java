package ru.barkhatnat.cinema.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.barkhatnat.cinema.dto.create.RowCreateDto;
import ru.barkhatnat.cinema.dto.regular.RowDto;
import ru.barkhatnat.cinema.dto.update.RowUpdateDto;
import ru.barkhatnat.cinema.service.RowService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rest/rows")
@RequiredArgsConstructor
public class RowRestController {

    private final RowService rowService;

    @DeleteMapping
    public ResponseEntity<Void> deleteById(@RequestBody UUID id) {
        rowService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<RowDto>> findAll() {
        return ResponseEntity.ok(rowService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RowDto> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(rowService.findById(id));
    }

    @PostMapping
    public ResponseEntity<RowDto> create(@RequestBody @Valid RowCreateDto rowCreateDto) {
        return ResponseEntity.ok(rowService.create(rowCreateDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RowUpdateDto> update(@PathVariable UUID id, @RequestBody RowUpdateDto rowUpdateDto) {
        return ResponseEntity.ok(rowService.update(id, rowUpdateDto));
    }
}