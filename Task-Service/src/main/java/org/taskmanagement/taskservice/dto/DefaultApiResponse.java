package org.taskmanagement.taskservice.dto;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record DefaultApiResponse(
        String message,
        HttpStatus statusCode,
        LocalDateTime actionPerformed
) {
}
