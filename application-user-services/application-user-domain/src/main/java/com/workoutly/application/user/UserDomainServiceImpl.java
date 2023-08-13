package com.workoutly.application.user;

import com.workoutly.application.user.event.UserActivatedEvent;
import com.workoutly.application.user.event.UserCreatedEvent;
import com.workoutly.application.user.event.UserUpdatedEvent;

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

    @Override
    public UserUpdatedEvent changeEmail(User user, String email) {
        user.changeEmail(email);
        return new UserUpdatedEvent(user.createSnapshot(), "Your email address has been changed.");
    }

    @Override
    public UserUpdatedEvent changePassword(User user, String newPassword) {
        user.changePassword(newPassword);
        return new UserUpdatedEvent(user.createSnapshot(), "Your password has been changed.");
    }
}
