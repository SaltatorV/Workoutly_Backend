package com.workoutly.application.user.event;

import com.workoutly.application.user.VO.UserSnapshot;

public class UserCreatedEvent {
    private final UserSnapshot snapshot;

    public UserCreatedEvent(UserSnapshot snapshot) {
        this.snapshot = snapshot;
    }

    public UserSnapshot getSnapshot() {
        return snapshot;
    }
}
