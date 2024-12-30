package ru.barkhatnat.cinema.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.barkhatnat.cinema.domain.Movie;
import ru.barkhatnat.cinema.dto.create.MovieCreateDto;
import ru.barkhatnat.cinema.dto.regular.MovieDto;
import ru.barkhatnat.cinema.dto.update.MovieUpdateDto;
import ru.barkhatnat.cinema.mapper.MovieMapper;
import ru.barkhatnat.cinema.repository.MovieRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/rest/movies")
@RequiredArgsConstructor
public class MovieRestController {

    private final MovieRepository movieRepository;

    private final MovieMapper movieMapper;

    private final ObjectMapper objectMapper;

    @DeleteMapping
    public ResponseEntity<Void> deleteById(@RequestBody UUID id) {
        movieRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<MovieDto>> findAll() {
        List<Movie> movies = movieRepository.findAll();
        List<MovieDto> movieDtos = movies.stream()
                .map(movieMapper::toMovieDto)
                .toList();
        return ResponseEntity.ok(movieDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDto> findById(@PathVariable UUID id) {
        Optional<Movie> movieOptional = movieRepository.findById(id);
        MovieDto movieDto = movieMapper.toMovieDto(movieOptional.orElse(null));
        return ResponseEntity.ok(movieDto);
    }

    @PostMapping
    public ResponseEntity<MovieDto> create(@RequestBody @Valid MovieCreateDto movieCreateDto) {
        Movie movie = movieMapper.toEntity(movieCreateDto);
        Movie resultMovie = movieRepository.save(movie);
        MovieDto movieDto = movieMapper.toMovieDto(resultMovie);
        return ResponseEntity.ok(movieDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieUpdateDto> update(@PathVariable UUID id, @RequestBody MovieUpdateDto movieUpdateDto){
        Movie movie = movieRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));
        movieMapper.updateWithNull(movieUpdateDto, movie);
        Movie resultMovie = movieRepository.save(movie);
        return ResponseEntity.ok(movieMapper.toMovieUpdateDto(resultMovie));
    }
}
