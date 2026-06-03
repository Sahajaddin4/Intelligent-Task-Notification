package org.taskmanagement.taskservice.globalexception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.taskmanagement.taskservice.dto.exception.ExceptionResponse;
import org.taskmanagement.taskservice.globalexception.customException.TaskNotFound;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        Map<String, String> validationErrors = new HashMap<>();
       ex.getBindingResult().getFieldErrors().forEach((error) -> {
           errors.put(error.getField(), error.getDefaultMessage());
       });
       validationErrors.put("error",errors.toString());
        ExceptionResponse response = new ExceptionResponse(
               HttpStatus.BAD_REQUEST,
               ex.getMessage(),
               LocalDateTime.now(),
                errors
       );
        return new
                ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(TaskNotFound.class)
    public ResponseEntity<ExceptionResponse> handleTaskNotFound(TaskNotFound ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", ex.getMessage());
        ExceptionResponse response = new ExceptionResponse(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                LocalDateTime.now(),
                errors
        );
        return  new
                ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error","Server Error");
        ExceptionResponse response = new ExceptionResponse(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                LocalDateTime.now(),
                errors
        );

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
