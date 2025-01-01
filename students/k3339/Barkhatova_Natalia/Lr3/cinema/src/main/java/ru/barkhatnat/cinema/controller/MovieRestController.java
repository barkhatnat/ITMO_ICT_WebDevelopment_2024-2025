package ru.barkhatnat.cinema.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.barkhatnat.cinema.dto.create.MovieCreateDto;
import ru.barkhatnat.cinema.dto.regular.MovieDto;
import ru.barkhatnat.cinema.dto.update.MovieUpdateDto;
import ru.barkhatnat.cinema.service.MovieService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rest/movies")
@RequiredArgsConstructor
public class MovieRestController {

    private final MovieService movieService;

    @DeleteMapping
    public ResponseEntity<Void> deleteById(@RequestBody UUID id) {
        movieService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<MovieDto>> findAll() {
        return ResponseEntity.ok(movieService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDto> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(movieService.findById(id));
    }

    @PostMapping
    public ResponseEntity<MovieDto> create(@RequestBody @Valid MovieCreateDto movieCreateDto) {
        return ResponseEntity.ok(movieService.create(movieCreateDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieUpdateDto> update(@PathVariable UUID id, @RequestBody MovieUpdateDto movieUpdateDto) {
        return ResponseEntity.ok(movieService.update(id, movieUpdateDto));
    }
}