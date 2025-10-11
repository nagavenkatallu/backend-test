package com.smart.pantry.backend.application.exception;

import com.smart.pantry.backend.application.model.User;

public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException(String message){
        super(message);
    }
}
