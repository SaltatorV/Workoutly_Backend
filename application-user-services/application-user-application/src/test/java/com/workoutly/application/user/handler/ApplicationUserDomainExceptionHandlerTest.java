package com.workoutly.application.user.handler;

import com.workoutly.application.user.exception.ApplicationUserDomainException;
import com.workoutly.common.exception.ErrorResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ApplicationUserDomainExceptionHandlerTest {

    ApplicationUserDomainExceptionHandler handler = new ApplicationUserDomainExceptionHandler();
    ErrorResponse response;

    @Test
    public void testHandleError() {
        //given
        ApplicationUserDomainException exception = createDomainException("Example error message");

        //when
        handleException(exception);

        //then
        responseMessageIs("Example error message");
        responseCodeIs("Bad Request");
    }

    private ApplicationUserDomainException createDomainException(String message) {
        return new ApplicationUserDomainException(message);
    }

    private void handleException(ApplicationUserDomainException exception) {
        response = handler.handleException(exception);
    }

    private void responseMessageIs(String message) {
        Assertions.assertEquals( message, response.getMessage());
    }
    private void responseCodeIs(String code) {
        Assertions.assertEquals( code, response.getCode());
    }
}
