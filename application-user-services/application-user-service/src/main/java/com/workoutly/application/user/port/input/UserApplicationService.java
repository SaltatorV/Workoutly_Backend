package com.workoutly.application.user.port.input;

import com.workoutly.application.user.dto.command.RegisterUserCommand;
import com.workoutly.application.user.dto.response.RegisterUserResponse;

public interface UserApplicationService {

    RegisterUserResponse createCommonUser(RegisterUserCommand registerUserCommand);
}
