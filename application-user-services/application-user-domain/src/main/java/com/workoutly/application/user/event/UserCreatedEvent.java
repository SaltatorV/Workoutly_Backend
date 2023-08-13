package com.workoutly.application.user.event;

import com.workoutly.application.user.VO.UserSnapshot;

public class UserCreatedEvent extends UserEvent{
    public UserCreatedEvent(UserSnapshot snapshot) {
        super(snapshot);
    }
}
