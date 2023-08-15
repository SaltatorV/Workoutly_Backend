package com.workoutly.measurement.VO;

import com.workoutly.common.VO.BaseId;

import java.util.UUID;

public class BodyWeightId extends BaseId<UUID> {
    public BodyWeightId(UUID id) {
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