package com.workoutly.application.user.mapper;

import com.workoutly.application.user.User;
import com.workoutly.application.user.VO.UserRole;
import com.workoutly.application.user.dto.command.RegisterUserCommand;
import com.workoutly.application.user.dto.response.RegisterUserResponse;
import com.workoutly.application.user.event.UserCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDataMapper {
    private final PasswordEncoder passwordEncoder;

    public User registerUserCommandToCommonUser(RegisterUserCommand registerUserCommand) {
        return new User(registerUserCommand.getUsername(),
                passwordEncoder.encode(registerUserCommand.getPassword()),
                registerUserCommand.getEmail(),
                UserRole.COMMON_USER);
    }

    public RegisterUserResponse userCreatedEventToRegisterUserResponse(UserCreatedEvent event) {
        return new RegisterUserResponse(
                "User created successfully, check your e-mail address to activate account.",
                event.getSnapshot().getUsername()
        );
    }
}
