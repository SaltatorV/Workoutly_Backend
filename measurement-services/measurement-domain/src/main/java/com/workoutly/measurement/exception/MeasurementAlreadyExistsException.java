package com.workoutly.measurement.exception;

public class MeasurementAlreadyExistsException extends MeasurementDomainException{
    public MeasurementAlreadyExistsException() {
        super("Measurement data from that day already exists. Delete them or make modifications to them.");
    }
}
