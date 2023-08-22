package com.workoutly.measurement;

import com.workoutly.measurement.event.BodyMeasurementCreatedEvent;
import com.workoutly.measurement.event.BodyWeightCreatedEvent;

public class MeasurementDomainServiceImpl implements MeasurementDomainService{

    @Override
    public BodyWeightCreatedEvent initializeBodyWeight(BodyWeight bodyWeight, String username) {
        bodyWeight.initialize(username);
        return new BodyWeightCreatedEvent(bodyWeight.createSnapshot());
    }

    @Override
    public BodyMeasurementCreatedEvent initializeBodyMeasurement(BodyMeasurement bodyMeasurement, String username) {
        bodyMeasurement.initialize(username);
        return new BodyMeasurementCreatedEvent(bodyMeasurement.createSnapshot());
    }
}