package com.workoutly.measurement.event;

import com.workoutly.measurement.VO.BodyMeasurementSnapshot;

public class BodyMeasurementUpdatedEvent extends BodyMeasurementEvent{
    public BodyMeasurementUpdatedEvent(BodyMeasurementSnapshot snapshot) {
        super(snapshot);
    }
}
