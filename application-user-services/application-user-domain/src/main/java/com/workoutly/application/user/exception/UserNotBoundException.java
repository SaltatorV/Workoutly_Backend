package com.workoutly.application.user.exception;

public class UserNotBoundException extends ApplicationUserDomainException{
    public UserNotBoundException() {
        super("User with provided token does not exist.");
    }
}
