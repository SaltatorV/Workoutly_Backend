package com.workoutly.application.user.handler;

import com.workoutly.application.user.exception.ApplicationUserDomainException;
import com.workoutly.common.exception.DomainExceptionHandler;
import com.workoutly.common.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.workoutly.common.exception.ErrorResponse.anErrorResponse;

@ControllerAdvice
public class ApplicationUserDomainExceptionHandler implements DomainExceptionHandler<ApplicationUserDomainException> {

    @ResponseBody
    @ExceptionHandler(value = {ApplicationUserDomainException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleException(ApplicationUserDomainException exception) {
        return anErrorResponse()
                .withCode(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .withMessage(exception.getMessage())
                .build();
    }
}
