package com.workoutly.measurement.exception;

public class MeasurementNotExistsException extends MeasurementDomainException{
    public MeasurementNotExistsException() {
        super("Measurement data from that day does not exist or has been deleted.");
    }
}
