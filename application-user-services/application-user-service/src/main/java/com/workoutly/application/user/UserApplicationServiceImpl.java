package com.workoutly.application.user;

import com.workoutly.application.user.dto.command.RegisterUserCommand;
import com.workoutly.application.user.dto.response.RegisterUserResponse;
import com.workoutly.application.user.port.input.UserApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class UserApplicationServiceImpl implements UserApplicationService {

    @Override
    public RegisterUserResponse createCommonUser(RegisterUserCommand registerUserCommand) {
        return new RegisterUserResponse(
                String.format("User: %s created successfully, check your e-mail address to activate account", registerUserCommand.getUsername()),
                registerUserCommand.getUsername()
        );
    }
}
