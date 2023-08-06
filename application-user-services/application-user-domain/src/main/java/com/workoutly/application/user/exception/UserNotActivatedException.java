package com.workoutly.application.user.exception;

public class UserNotActivatedException extends ApplicationUserDomainException{
    public UserNotActivatedException() {
        super("User is not activated.");
    }
}
