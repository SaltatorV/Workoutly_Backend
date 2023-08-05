package com.workoutly.application.user.exception;

public class VerificationTokenExpiredException extends ApplicationUserDomainException{
    public VerificationTokenExpiredException() {
        super("Provided token expired. Try to register again.");
    }
}
