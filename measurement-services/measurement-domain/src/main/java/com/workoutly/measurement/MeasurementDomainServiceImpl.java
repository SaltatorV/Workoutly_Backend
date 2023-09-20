package com.workoutly.measurement;

import com.workoutly.measurement.VO.BodyMeasurementSnapshot;
import com.workoutly.measurement.VO.BodyWeightSnapshot;
import com.workoutly.measurement.event.BodyMeasurementCreatedEvent;
import com.workoutly.measurement.event.BodyMeasurementUpdatedEvent;
import com.workoutly.measurement.event.BodyWeightCreatedEvent;
import com.workoutly.measurement.event.BodyWeightUpdatedEvent;

public class MeasurementDomainServiceImpl implements MeasurementDomainService{

    @Override
    public BodyWeightCreatedEvent initializeBodyWeight(BodyWeight bodyWeight, String username) {
        bodyWeight.initialize(username);
        return new BodyWeightCreatedEvent(bodyWeight.createSnapshot());
    }

    @Override
    public BodyWeightUpdatedEvent updateBodyWeight(BodyWeight bodyWeightToUpdate, BodyWeightSnapshot snapshot) {
        bodyWeightToUpdate.updateValues(snapshot);
        return new BodyWeightUpdatedEvent(bodyWeightToUpdate.createSnapshot());
    }

    @Override
    public BodyMeasurementCreatedEvent initializeBodyMeasurement(BodyMeasurement bodyMeasurement, String username) {
        bodyMeasurement.initialize(username);
        return new BodyMeasurementCreatedEvent(bodyMeasurement.createSnapshot());
    }

    @Override
    public BodyMeasurementUpdatedEvent updateBodyMeasurement(BodyMeasurement measurementToUpdate, BodyMeasurementSnapshot snapshot) {
        measurementToUpdate.updateValues(snapshot);
        return new BodyMeasurementUpdatedEvent(measurementToUpdate.createSnapshot());
    }
}
