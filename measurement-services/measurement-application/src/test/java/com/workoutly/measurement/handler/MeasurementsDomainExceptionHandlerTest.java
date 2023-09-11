package com.workoutly.measurement.handler;

import com.workoutly.common.exception.ErrorResponse;
import com.workoutly.measurement.exception.MeasurementDomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MeasurementsDomainExceptionHandlerTest {

    private MeasurementsDomainExceptionHandler handler = new MeasurementsDomainExceptionHandler();
    private ErrorResponse response;

    @Test
    public void testHandleError() {
        //given
        MeasurementDomainException exception = createDomainException("Example error message");

        //when
        handleException(exception);

        //then
        responseMessageIs("Example error message");
        responseCodeIs("Bad Request");
    }

    private MeasurementDomainException createDomainException(String message) {
        return new MeasurementDomainException(message);
    }

    private void handleException(MeasurementDomainException exception) {
        response = handler.handleException(exception);
    }

    private void responseMessageIs(String message) {
        Assertions.assertEquals( message, response.getMessage());
    }
    private void responseCodeIs(String code) {
        Assertions.assertEquals( code, response.getCode());
    }
}
