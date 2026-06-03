package org.taskmanagement.authservice.dto.response;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
    int statusCode;
    String message;

}
