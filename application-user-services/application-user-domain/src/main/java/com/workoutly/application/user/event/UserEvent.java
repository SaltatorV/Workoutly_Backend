package com.workoutly.application.user.event;

import com.workoutly.application.user.VO.UserSnapshot;
import com.workoutly.common.event.DomainEvent;

public class UserEvent implements DomainEvent<UserSnapshot> {
    private final UserSnapshot snapshot;

    public UserEvent(UserSnapshot snapshot) {
        this.snapshot = snapshot;
    }

    public UserSnapshot getSnapshot() {
        return snapshot;
    }
}
