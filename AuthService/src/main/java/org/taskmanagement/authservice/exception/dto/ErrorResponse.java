package org.taskmanagement.authservice.exception.dto;

import java.time.LocalDateTime;

public record ErrorResponse(
        String errror,
        int statusCode,
        LocalDateTime errorTime
) {
}
