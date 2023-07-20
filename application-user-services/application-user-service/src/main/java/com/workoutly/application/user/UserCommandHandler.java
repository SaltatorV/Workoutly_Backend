package com.workoutly.application.user;

import com.workoutly.application.user.VO.EmailAddress;
import com.workoutly.application.user.VO.Password;
import com.workoutly.application.user.VO.UserRole;
import com.workoutly.application.user.VO.Username;
import com.workoutly.application.user.dto.command.RegisterUserCommand;
import com.workoutly.application.user.event.UserCreatedEvent;

class UserCommandHandler {
    UserCreatedEvent createCommonUser(RegisterUserCommand registerUserCommand) {
        User user = new User(new Username(registerUserCommand.getUsername()),
                new Password(registerUserCommand.getPassword()),
                new EmailAddress(registerUserCommand.getEmail()),
                UserRole.COMMON);

        //create User
        user.initialize();

        return new UserCreatedEvent(user.createSnapshot());
    }
}
