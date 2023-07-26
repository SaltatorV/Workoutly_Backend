package com.workoutly.application.user;

import com.workoutly.application.user.dto.command.RegisterUserCommand;
import com.workoutly.application.user.dto.response.RegisterUserResponse;
import com.workoutly.application.user.port.input.UserApplicationService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Validated
@Service
public class UserApplicationServiceImpl implements UserApplicationService {

    @Override
    public RegisterUserResponse createCommonUser(RegisterUserCommand registerUserCommand) {
        return null;
    }
}
