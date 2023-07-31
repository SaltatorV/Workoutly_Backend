package com.workoutly.application.user.exception;

public class UserNotRegisteredException extends ApplicationUserDomainException{
    public UserNotRegisteredException() {
        super("Cannot register a new user.");
    }
}
