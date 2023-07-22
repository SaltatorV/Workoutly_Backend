package com.workoutly.application.user;

import com.workoutly.application.user.event.UserCreatedEvent;

public class UserDomainServiceImpl implements UserDomainService{
    @Override
    public UserCreatedEvent initializeUser(User user) {
        user.initialize();
        return new UserCreatedEvent(user.createSnapshot());
    }
}
