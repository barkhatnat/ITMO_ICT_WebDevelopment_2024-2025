package ru.barkhatnat.cinema.dto.create;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

/**
 * DTO for {@link ru.barkhatnat.cinema.domain.User}
 */
public record UserCreateDto(
        @Size(message = "Email must be at most 50 characters", max = 50)
        @Email(message = "Invalid email format")
        @NotBlank(message = "Email cannot be blank")
        String email,

        @NotBlank(message = "Password cannot be blank")
        String password,

        @Size(message = "First name must be at most 50 characters", max = 50)
        @NotBlank(message = "First name cannot be blank")
        String firstName,

        @Size(message = "Last name must be at most 50 characters", max = 50)
        @NotBlank(message = "Last name cannot be blank")
        String lastName,

        @Size(message = "Middle name must be at most 50 characters", max = 50)
        String middleName,

        @NotNull(message = "Role cannot be null")
        UUID roleId) {
}