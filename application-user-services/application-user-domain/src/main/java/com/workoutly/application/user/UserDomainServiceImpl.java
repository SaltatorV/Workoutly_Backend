package com.workoutly.application.user;

import com.workoutly.application.user.event.UserActivatedEvent;
import com.workoutly.application.user.event.UserCreatedEvent;

public class UserDomainServiceImpl implements UserDomainService{
    @Override
    public UserCreatedEvent initializeUser(User user) {
        user.initialize();
        return new UserCreatedEvent(user.createSnapshot());
    }

    @Override
    public UserActivatedEvent activateUser(User user) {
        user.enable();
        return new UserActivatedEvent(user.createSnapshot());
    }
}
