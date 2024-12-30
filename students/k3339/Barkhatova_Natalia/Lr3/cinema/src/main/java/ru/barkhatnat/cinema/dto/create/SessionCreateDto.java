package ru.barkhatnat.cinema.dto.create;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import ru.barkhatnat.cinema.dto.regular.HallDto;
import ru.barkhatnat.cinema.dto.regular.MovieDto;

import java.time.LocalDateTime;

/**
 * DTO for {@link ru.barkhatnat.cinema.domain.Session}
 */
public record SessionCreateDto(
        @NotNull(message = "Movie cannot be null")
        MovieDto movie,

        @NotNull(message = "Hall cannot be null")
        HallDto hall,

        @NotNull(message = "Start time cannot be null")
        LocalDateTime startTime,

        @Min(message = "Price must be greater than or equal to 0", value = 0)
        Integer price) {
}