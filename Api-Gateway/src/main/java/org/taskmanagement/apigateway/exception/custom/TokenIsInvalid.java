package org.taskmanagement.apigateway.exception.custom;

public class TokenIsInvalid extends RuntimeException {
    public TokenIsInvalid(String message) {
        super(message);
    }
}
