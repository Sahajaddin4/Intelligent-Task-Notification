package org.taskmanagement.taskservice.dto.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {
    HttpStatus statusCode;
    String errorMessage;
    LocalDateTime exceptionTime;
    Map<String,String> errorDetails;
}
