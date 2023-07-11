package com.workoutly.application.user.exception;

import com.workoutly.common.exception.DomainException;

public class ApplicationUserDomainException extends DomainException {
    public ApplicationUserDomainException(String message) {
        super(message);
    }
}
