package com.workoutly.measurement.event;

import com.workoutly.common.event.DomainEvent;
import com.workoutly.measurement.VO.BodyWeightSnapshot;

public class BodyWeightEvent implements DomainEvent<BodyWeightSnapshot> {
    private final BodyWeightSnapshot snapshot;

    public BodyWeightEvent(BodyWeightSnapshot snapshot) {
        this.snapshot = snapshot;
    }

    public BodyWeightSnapshot getSnapshot() {
        return snapshot;
    }
}
