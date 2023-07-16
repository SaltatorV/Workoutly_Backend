package com.workoutly.application.user.VO;

import com.workoutly.common.VO.BaseId;

import java.util.UUID;

public class UserId extends BaseId<UUID> {
    public UserId(UUID id) {
        super(id);
    }

    public String getValue(){
        return getId().toString();
    }

    @Override
    public String toString() {
        return getId();
    }
}
