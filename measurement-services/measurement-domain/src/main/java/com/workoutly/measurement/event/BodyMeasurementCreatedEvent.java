package com.workoutly.measurement.event;


import com.workoutly.measurement.VO.BodyMeasurementSnapshot;

public class BodyMeasurementCreatedEvent extends BodyMeasurementEvent {

    public BodyMeasurementCreatedEvent(BodyMeasurementSnapshot snapshot) {
        super(snapshot);
    }
}
