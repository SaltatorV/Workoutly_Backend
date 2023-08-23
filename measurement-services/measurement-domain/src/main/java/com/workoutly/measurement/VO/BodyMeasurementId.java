package com.workoutly.measurement.VO;

import com.workoutly.common.VO.BaseId;

import java.util.UUID;

public class BodyMeasurementId extends BaseId<UUID> {
    public BodyMeasurementId(UUID id) {
        super(id);
    }

    public String getValue() {
        return getId().toString();
    }

    @Override
    public String toString() {
        return getId();
    }

}