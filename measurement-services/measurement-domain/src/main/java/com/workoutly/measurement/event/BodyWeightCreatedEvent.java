package com.workoutly.measurement.event;

import com.workoutly.common.event.DomainEvent;
import com.workoutly.measurement.VO.BodyWeightSnapshot;

public class BodyWeightCreatedEvent implements DomainEvent<BodyWeightSnapshot> {
    private final BodyWeightSnapshot snapshot;

    public BodyWeightCreatedEvent(BodyWeightSnapshot snapshot) {
        this.snapshot = snapshot;
    }

    public BodyWeightSnapshot getSnapshot() {
        return snapshot;
    }
}
