package com.workoutly.measurement.exception;

public class BodyMeasurementAlreadyExistsException extends MeasurementDomainException{
    public BodyMeasurementAlreadyExistsException() {
        super("Measurement data from that day already exists. Delete them or make modifications to them.");
    }
}
