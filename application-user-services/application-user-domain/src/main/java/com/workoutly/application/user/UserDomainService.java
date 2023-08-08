package com.workoutly.application.user;

import com.workoutly.application.user.VO.UserSnapshot;
import com.workoutly.application.user.event.UserActivatedEvent;
import com.workoutly.application.user.event.UserCreatedEvent;
import com.workoutly.application.user.event.UserUpdateEvent;

public interface UserDomainService {
    UserCreatedEvent initializeUser(User user);

    UserActivatedEvent activateUser(User user);

    UserUpdateEvent changeEmail(UserSnapshot userSnapshot);

    UserUpdateEvent changePassword(UserSnapshot userSnapshot);
}
