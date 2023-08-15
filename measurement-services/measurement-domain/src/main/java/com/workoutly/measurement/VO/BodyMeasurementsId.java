package com.workoutly.measurement.VO;

import com.workoutly.common.VO.BaseId;

import java.util.UUID;

public class BodyMeasurementsId extends BaseId<UUID> {
    public BodyMeasurementsId(UUID id) {
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