package com.workoutly.application.user.event;

import com.workoutly.application.user.VO.UserSnapshot;

public class UserChangedEvent {
    private final UserSnapshot snapshot;
    private final String message;

    public UserChangedEvent(UserSnapshot snapshot, String message) {
        this.snapshot = snapshot;
        this.message = message;
    }

    public UserSnapshot getSnapshot() {
        return snapshot;
    }

    public String getMessage() {
        return message;
    }
}
