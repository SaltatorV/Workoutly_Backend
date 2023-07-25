package com.workoutly.common.exception;

public interface DomainExceptionHandler<T> {
    public ErrorResponse handleException(T exception);
}
