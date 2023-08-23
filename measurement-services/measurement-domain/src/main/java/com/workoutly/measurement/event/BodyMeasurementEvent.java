package com.workoutly.measurement.event;

import com.workoutly.common.event.DomainEvent;
import com.workoutly.measurement.VO.BodyMeasurementSnapshot;

public class BodyMeasurementEvent implements DomainEvent<BodyMeasurementSnapshot> {
    private final BodyMeasurementSnapshot snapshot;

    public BodyMeasurementEvent(BodyMeasurementSnapshot snapshot) {
        this.snapshot = snapshot;
    }

    public BodyMeasurementSnapshot getSnapshot() {
        return snapshot;
    }
}
