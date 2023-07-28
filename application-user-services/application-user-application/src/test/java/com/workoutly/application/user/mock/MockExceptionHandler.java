package com.workoutly.application.user.mock;

import com.workoutly.common.exception.DomainExceptionHandler;
import com.workoutly.common.exception.ErrorResponse;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.workoutly.common.exception.ErrorResponse.anErrorResponse;

@ControllerAdvice
public class MockExceptionHandler implements DomainExceptionHandler<ValidationException> {

    @Override
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {ValidationException.class})
    public ErrorResponse handleException(ValidationException exception) {
        return createErrorResponse();
    }

    public static ErrorResponse createErrorResponse(){
        return anErrorResponse()
                .withCode(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .withMessage("Constraint validation error")
                .build();
    }
}
