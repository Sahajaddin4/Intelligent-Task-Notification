package org.taskmanagement.taskservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.taskmanagement.taskservice.customannotationrule.ValidEndTime;


import java.time.LocalDateTime;

public record TaskDetailsDto(
        @NotBlank
        String name,

        String description,
        @NotNull
        Long userId,
        @NotNull
        @ValidEndTime
        LocalDateTime endTime
) {}