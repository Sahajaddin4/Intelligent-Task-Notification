package org.taskmanagement.taskservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.taskmanagement.taskservice.customannotationrule.ValidEndTime;


import java.time.LocalDateTime;

public record TaskDetailsDto(
        @NotBlank
        String name,
        Long id,
        String description,
        @NotNull
        Long userId,
        @NotNull
        @ValidEndTime
        LocalDateTime executionTime
) {}