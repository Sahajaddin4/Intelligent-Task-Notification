package org.taskmanagement.apigateway.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    String error;
    int statusCode;
    String path;
    LocalDateTime failedAt;
}
