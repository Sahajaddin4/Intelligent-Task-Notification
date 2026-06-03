package org.taskmanagement.userservice.dto.response;

import lombok.*;

import java.util.Map;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ApiErrorResponse extends ApiResponse {
     Map<String, String> errors;
}
