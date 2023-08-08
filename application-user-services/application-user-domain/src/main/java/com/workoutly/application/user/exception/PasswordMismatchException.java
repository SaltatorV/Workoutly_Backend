package com.workoutly.application.user.exception;

public class PasswordMismatchException extends ApplicationUserDomainException{
    public PasswordMismatchException() {
        super("Provided password is wrong..");
    }
}
