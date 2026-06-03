package org.taskmanagement.userservice.exception.customException;

public class ServiceCommunicationException extends RuntimeException {
    public int getStatusCode() {
        return statusCode;
    }

    private final int statusCode;
    public ServiceCommunicationException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
