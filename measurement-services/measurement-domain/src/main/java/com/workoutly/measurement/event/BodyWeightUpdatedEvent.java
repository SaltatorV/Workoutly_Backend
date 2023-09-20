package com.workoutly.measurement.event;
import com.workoutly.measurement.VO.BodyWeightSnapshot;

public class BodyWeightUpdatedEvent extends BodyWeightEvent{
    public BodyWeightUpdatedEvent(BodyWeightSnapshot snapshot) {
        super(snapshot);
    }
}
