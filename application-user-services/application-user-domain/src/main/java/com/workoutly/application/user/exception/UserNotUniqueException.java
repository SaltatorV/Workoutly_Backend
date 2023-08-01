package com.workoutly.application.user.exception;

public class UserNotUniqueException extends ApplicationUserDomainException{
    public UserNotUniqueException() {
        super("User with provided username or email already exists.");
    }
}
