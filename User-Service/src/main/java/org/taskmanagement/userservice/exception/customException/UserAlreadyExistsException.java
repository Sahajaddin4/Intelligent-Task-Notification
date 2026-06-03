 package org.taskmanagement.userservice.exception.customException;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String error){
        super(error);
    }
}