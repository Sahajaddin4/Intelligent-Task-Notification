package org.taskmanagement.taskservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record DefaultApiResponse(
        String message,
        HttpStatus statusCode,
        @JsonFormat(pattern = "dd MMM yyyy 'at' hh:mm a")
        LocalDateTime actionPerformed
) {
}
