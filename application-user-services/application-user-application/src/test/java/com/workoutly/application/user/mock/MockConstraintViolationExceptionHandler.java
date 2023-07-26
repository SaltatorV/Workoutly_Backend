package com.workoutly.application.user.mock;

import com.workoutly.common.exception.ErrorResponse;
import com.workoutly.common.exception.DomainExceptionHandler;
import jakarta.servlet.ServletException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

import static com.workoutly.common.exception.ErrorResponse.anErrorResponse;

@Component
@RestControllerAdvice
public class MockConstraintViolationExceptionHandler implements DomainExceptionHandler<ConstraintViolationException> {

    @Override
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {ConstraintViolationException.class})
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
