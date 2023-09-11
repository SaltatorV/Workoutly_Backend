package com.workoutly.measurement.handler;

import com.workoutly.common.exception.DomainExceptionHandler;
import com.workoutly.common.exception.ErrorResponse;
import com.workoutly.measurement.exception.MeasurementDomainException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.workoutly.common.exception.ErrorResponse.anErrorResponse;

@ControllerAdvice
public class MeasurementsDomainExceptionHandler implements DomainExceptionHandler<MeasurementDomainException> {

    @ResponseBody
    @ExceptionHandler(value = {MeasurementDomainException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleException(MeasurementDomainException exception) {
        return anErrorResponse()
                .withCode(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .withMessage(exception.getMessage())
                .build();
    }
}
