package com.workoutly.application.user.mapper;

import com.workoutly.application.user.User;
import com.workoutly.application.user.VO.UserRole;
import com.workoutly.application.user.dto.command.RegisterUserCommand;
import org.springframework.stereotype.Component;

@Component
public class UserDataMapper {
    public User registerUserCommandToCommonUser(RegisterUserCommand registerUserCommand) {
        return new User(registerUserCommand.getUsername(),
                registerUserCommand.getPassword(),
                registerUserCommand.getEmail(),
                UserRole.COMMON);
    }
}
