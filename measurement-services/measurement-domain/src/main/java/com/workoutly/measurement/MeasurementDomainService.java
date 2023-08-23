package com.workoutly.measurement;

import com.workoutly.measurement.VO.BodyMeasurementSnapshot;
import com.workoutly.measurement.event.BodyMeasurementCreatedEvent;
import com.workoutly.measurement.event.BodyMeasurementUpdatedEvent;
import com.workoutly.measurement.event.BodyWeightCreatedEvent;

public interface MeasurementDomainService {
    BodyWeightCreatedEvent initializeBodyWeight(BodyWeight bodyWeight, String username);

    BodyMeasurementCreatedEvent initializeBodyMeasurement(BodyMeasurement bodyMeasurement, String username);

    BodyMeasurementUpdatedEvent updateBodyMeasurement(BodyMeasurement measurementToUpdate, BodyMeasurementSnapshot snapshot);
}
