package com.workoutly.measurement.exception;

public class BodyMeasurementNotExistsException extends MeasurementDomainException{
    public BodyMeasurementNotExistsException() {
        super("Measurement data from that day does not exist or has been deleted.");
    }
}
