package org.taskmanagement.authservice.exception.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ErrorResponse(
        String errror,
        int statusCode,
        @JsonFormat(pattern = "dd MMM yyyy 'at' hh:mm a")
        LocalDateTime errorTime
) {
}
