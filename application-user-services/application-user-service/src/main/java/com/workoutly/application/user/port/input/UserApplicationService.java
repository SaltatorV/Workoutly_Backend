package com.workoutly.application.user.port.input;

import com.workoutly.application.user.dto.command.RegisterUserCommand;
import com.workoutly.application.user.dto.response.RegisterUserResponse;
import jakarta.validation.Valid;

public interface UserApplicationService {

    RegisterUserResponse createCommonUser(@Valid RegisterUserCommand registerUserCommand);
}
