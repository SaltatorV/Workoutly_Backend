package com.workoutly.application.user.exception;

public class UserNotFoundException extends ApplicationUserDomainException{
    public UserNotFoundException() {
        super("The user with the specified name was not found.");
    }
}
