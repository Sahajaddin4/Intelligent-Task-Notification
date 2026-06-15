package org.taskmanagement.authservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.taskmanagement.authservice.exception.custom.*;
import org.taskmanagement.authservice.exception.dto.ErrorResponse;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage(),HttpStatus.NOT_FOUND.value(), LocalDateTime.now()));
    }

    @ExceptionHandler({BadCredentialsException.class, InternalAuthenticationServiceException.class})
    public ResponseEntity<ErrorResponse> handleAuthenticationFailures(AuthenticationException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        "Invalid email or password",
                        HttpStatus.BAD_REQUEST.value(),
                        LocalDateTime.now()
                ));
    }
    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ErrorResponse> handleTokenExpiredException(TokenExpiredException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage(),HttpStatus.BAD_REQUEST.value(), LocalDateTime.now()));
    }



    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(e.getMessage(),HttpStatus.CONFLICT.value(), LocalDateTime.now()));
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage(),HttpStatus.NOT_FOUND.value(), LocalDateTime.now()));
    }
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage(),HttpStatus.BAD_REQUEST.value(), LocalDateTime.now()));
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.internalServerError().body(new ErrorResponse(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now()));
    }
}
