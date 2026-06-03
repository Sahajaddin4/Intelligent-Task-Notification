package org.taskmanagement.authservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginRequestDto(
        @NotBlank
        @Email
        @NotNull
        String email,
        @NotBlank
        @NotNull
        String password
) {
}
