package org.taskmanagement.authservice.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SignupRequestDto(
         String name,
        @Email(message = "Email is not valid.")
        @NotBlank
        @NotNull
         String email,
        @NotBlank
        @NotNull
         String password,
         String phoneNumber
) {
}
