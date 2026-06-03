package org.taskmanagement.apigateway.exception.custom;

public class TokenIsMissing extends RuntimeException {
    public TokenIsMissing(String message) {
        super(message);
    }
}
