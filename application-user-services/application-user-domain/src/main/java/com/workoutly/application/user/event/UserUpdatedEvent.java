package com.workoutly.application.user.event;

import com.workoutly.application.user.VO.UserSnapshot;

public class UserUpdatedEvent extends UserEvent{
    private final String message;

    public UserUpdatedEvent(UserSnapshot snapshot, String message) {
        super(snapshot);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
