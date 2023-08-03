package com.workoutly.application.user.event;

import com.workoutly.application.user.VO.UserSnapshot;

public class UserActivatedEvent {
    private final UserSnapshot snapshot;

    public UserActivatedEvent(UserSnapshot snapshot) {
        this.snapshot = snapshot;
    }

    public UserSnapshot getSnapshot() {
        return snapshot;
    }
}
