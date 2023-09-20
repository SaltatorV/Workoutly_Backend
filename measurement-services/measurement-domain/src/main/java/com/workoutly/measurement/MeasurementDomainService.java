package com.workoutly.measurement;

import com.workoutly.measurement.VO.BodyMeasurementSnapshot;
import com.workoutly.measurement.VO.BodyWeightSnapshot;
import com.workoutly.measurement.event.BodyMeasurementCreatedEvent;
import com.workoutly.measurement.event.BodyMeasurementUpdatedEvent;
import com.workoutly.measurement.event.BodyWeightCreatedEvent;
import com.workoutly.measurement.event.BodyWeightUpdatedEvent;

public interface MeasurementDomainService {
    BodyWeightCreatedEvent initializeBodyWeight(BodyWeight bodyWeight, String username);

    BodyWeightUpdatedEvent updateBodyWeight(BodyWeight bodyWeightToUpdate, BodyWeightSnapshot snapshot);

    BodyMeasurementCreatedEvent initializeBodyMeasurement(BodyMeasurement bodyMeasurement, String username);

    BodyMeasurementUpdatedEvent updateBodyMeasurement(BodyMeasurement measurementToUpdate, BodyMeasurementSnapshot snapshot);
}
