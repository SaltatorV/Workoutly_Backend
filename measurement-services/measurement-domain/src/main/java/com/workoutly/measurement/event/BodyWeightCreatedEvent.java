package com.workoutly.measurement.event;

import com.workoutly.measurement.VO.BodyWeightSnapshot;

public class BodyWeightCreatedEvent extends BodyWeightEvent {

    public BodyWeightCreatedEvent(BodyWeightSnapshot snapshot) {
        super(snapshot);
    }
}
