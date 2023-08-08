package com.workoutly.application.user;

import com.workoutly.application.user.VO.UserSnapshot;
import com.workoutly.application.user.event.UserActivatedEvent;
import com.workoutly.application.user.event.UserCreatedEvent;
import com.workoutly.application.user.event.UserUpdateEvent;

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
    public UserUpdateEvent changeEmail(User user, String email) {
        user.changeEmail(email);
        return new UserUpdateEvent(user.createSnapshot(), "Your email address has been changed.");
    }

    @Override
    public UserUpdateEvent changePassword(User user, String newPassword) {
        user.changePassword(newPassword);
        return new UserUpdateEvent(user.createSnapshot(), "Your password has been changed.");
    }
}
