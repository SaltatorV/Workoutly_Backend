package com.workoutly.application.user.event;

import com.workoutly.application.user.VO.UserSnapshot;

public class UserActivatedEvent extends UserEvent{
    public UserActivatedEvent(UserSnapshot snapshot) {
        super(snapshot);
    }
}
