package org.taskmanagement.authservice.dto.servicedto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUserAccount(
        String name,
        @Email(message = "Email is not valid.")
        @NotBlank
        String email,
        String phoneNumber
) {
}
