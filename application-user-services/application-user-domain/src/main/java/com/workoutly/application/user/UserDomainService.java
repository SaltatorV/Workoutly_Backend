package com.workoutly.application.user;

import com.workoutly.application.user.event.UserActivatedEvent;
import com.workoutly.application.user.event.UserCreatedEvent;

public interface UserDomainService {
    UserCreatedEvent initializeUser(User user);

    UserActivatedEvent activateUser(User user);
}
