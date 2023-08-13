package com.workoutly.application.user;

import com.workoutly.application.user.event.UserActivatedEvent;
import com.workoutly.application.user.event.UserCreatedEvent;
import com.workoutly.application.user.event.UserUpdatedEvent;

public interface UserDomainService {
    UserCreatedEvent initializeUser(User user);

    UserActivatedEvent activateUser(User user);

    UserUpdatedEvent changeEmail(User user, String email);

    UserUpdatedEvent changePassword(User user, String newPassword);
}
