package org.taskmanagement.taskservice.dto.request;

import java.time.LocalDateTime;

public record UpdateTaskDetails(
        String name,
        String description,
        LocalDateTime executionTime,
        Long taskId
) {
}
