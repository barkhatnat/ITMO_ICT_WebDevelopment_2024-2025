package ru.barkhatnat.cinema.dto.regular;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

/**
 * DTO for {@link ru.barkhatnat.cinema.domain.User}
 */
public record UserDto(
        UUID id,

        @Size(message = "Email must be at most 50 characters", max = 50)
        @Email(message = "Invalid email format")
        @NotBlank(message = "Email cannot be blank")
        String email,

        @Size(message = "Password must be between 6 and 50 characters", min = 6, max = 50)
        @NotBlank(message = "Password cannot be blank")
        String password,

        @Size(message = "First name must be at most 50 characters", max = 50)
        @NotBlank(message = "First name cannot be blank")
        String first_name,

        @Size(message = "Last name must be at most 50 characters", max = 50)
        @NotBlank(message = "Last name cannot be blank")
        String last_name,

        @Size(message = "Middle name must be at most 50 characters", max = 50)
        String middle_name,

        @NotNull(message = "Role cannot be null")
        RoleDto role) {
}