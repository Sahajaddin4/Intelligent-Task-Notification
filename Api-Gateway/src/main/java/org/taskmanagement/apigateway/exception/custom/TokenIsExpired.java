package org.taskmanagement.apigateway.exception.custom;

public class TokenIsExpired extends RuntimeException {
    public TokenIsExpired(String message) {
        super(message);
    }
}
