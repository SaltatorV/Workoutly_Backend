package com.workoutly.measurement.exception;

import com.workoutly.common.exception.DomainException;

public class MeasurementDomainException extends DomainException {
    public MeasurementDomainException(String message) {
        super(message);
    }
}
