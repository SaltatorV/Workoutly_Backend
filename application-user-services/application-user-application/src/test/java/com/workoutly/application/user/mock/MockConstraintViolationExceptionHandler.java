package com.workoutly.application.user.mock;

import com.workoutly.common.exception.ErrorResponse;
import com.workoutly.common.exception.DomainExceptionHandler;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.stream.Collectors;

import static com.workoutly.common.exception.ErrorResponse.anErrorResponse;

@ControllerAdvice
public class MockConstraintViolationExceptionHandler implements DomainExceptionHandler<ConstraintViolationException> {

    @Override
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorResponse handleException(ConstraintViolationException exception) {
        String message = convertViolations(exception);

        return anErrorResponse()
                .withCode(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .withMessage(message)
                .build();
    }

    private String convertViolations(ConstraintViolationException exception) {
        return exception.getConstraintViolations()
                .stream()
                .map(violation -> violation.getMessage())
                .collect(Collectors.joining("-"));
    }
}
