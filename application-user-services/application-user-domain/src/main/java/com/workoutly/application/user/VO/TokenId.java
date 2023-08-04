package com.workoutly.application.user.VO;

import com.workoutly.common.VO.BaseId;

import java.util.UUID;

public class TokenId extends BaseId<UUID> {
    public TokenId(UUID id) {
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
