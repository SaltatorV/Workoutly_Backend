package com.workoutly.application.user.handler;

import com.workoutly.application.user.exception.ApplicationUserDomainException;
import com.workoutly.common.exception.ErrorResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

public class UserConstraintViolationExceptionHandlerTest {

    private UserConstraintViolationExceptionHandler handler = new UserConstraintViolationExceptionHandler();
    private ErrorResponse response;

    @Test
    public void testHandleError() {
        //given
        ConstraintViolationException exception = createConstraintViolationException();

        //when
        handleException(exception);

        //then
        responseMessageIs("");
        responseCodeIs("Bad Request");
    }

    private ConstraintViolationException createConstraintViolationException() {
        Set<ConstraintViolation<?>> violations = new HashSet<>();
        return new ConstraintViolationException(violations);
    }

    private void handleException(ConstraintViolationException exception) {
        response = handler.handleException(exception);
    }

    private void responseMessageIs(String message) {
        System.out.println(response.getMessage());
        Assertions.assertEquals( message, response.getMessage());
    }
    private void responseCodeIs(String code) {
        System.out.println(response.getCode());
        Assertions.assertEquals( code, response.getCode());
    }
}
