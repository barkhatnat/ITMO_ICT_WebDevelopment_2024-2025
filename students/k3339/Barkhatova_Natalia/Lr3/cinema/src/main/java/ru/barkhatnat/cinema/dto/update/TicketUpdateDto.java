package ru.barkhatnat.cinema.dto.update;

import jakarta.validation.constraints.NotNull;
import ru.barkhatnat.cinema.dto.regular.UserDto;
import ru.barkhatnat.cinema.dto.regular.SeatDto;
import ru.barkhatnat.cinema.dto.regular.SessionDto;

import java.util.UUID;

/**
 * DTO for {@link ru.barkhatnat.cinema.domain.Ticket}
 */
public record TicketUpdateDto(
        @NotNull(message = "User cannot be null")
        UserDto user,

        @NotNull(message = "Session cannot be null")
        SessionDto session,

        @NotNull(message = "Seat cannot be null")
        SeatDto seat) {
}