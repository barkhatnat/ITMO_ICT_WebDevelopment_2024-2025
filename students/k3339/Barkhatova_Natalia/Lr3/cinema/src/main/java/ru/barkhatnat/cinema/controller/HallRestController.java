package ru.barkhatnat.cinema.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.barkhatnat.cinema.dto.create.HallCreateDto;
import ru.barkhatnat.cinema.dto.regular.HallDto;
import ru.barkhatnat.cinema.dto.update.HallUpdateDto;
import ru.barkhatnat.cinema.service.HallService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rest/admin/halls")
@RequiredArgsConstructor
public class HallRestController {

    private final HallService hallService;

    @DeleteMapping
    public ResponseEntity<Void> deleteById(@RequestBody UUID id) {
        hallService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<HallDto>> findAll() {
        return ResponseEntity.ok(hallService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HallDto> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(hallService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HallUpdateDto> update(@PathVariable UUID id, @RequestBody HallUpdateDto hallUpdateDto) {
        return ResponseEntity.ok(hallService.update(id, hallUpdateDto));
    }

    @PostMapping
    public ResponseEntity<HallDto> save(@RequestBody @Valid HallCreateDto hallCreateDto) {
        return ResponseEntity.ok(hallService.save(hallCreateDto));
    }
}