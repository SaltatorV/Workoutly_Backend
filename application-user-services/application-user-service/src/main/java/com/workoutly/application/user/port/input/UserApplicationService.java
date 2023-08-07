package com.workoutly.application.user.port.input;

import com.workoutly.application.user.dto.command.*;
import com.workoutly.application.user.dto.response.MessageResponse;
import com.workoutly.application.user.dto.response.RegisterUserResponse;
import com.workoutly.application.user.dto.response.TokenResponse;
import jakarta.validation.Valid;

public interface UserApplicationService {

    RegisterUserResponse createCommonUser(@Valid RegisterUserCommand registerUserCommand);
    MessageResponse activateUserAccount(@Valid ActivationUserCommand activationUserCommand);
    TokenResponse authenticate(@Valid AuthenticationCommand authenticationCommand);
    MessageResponse changeEmail(@Valid ChangeEmailCommand command);
    MessageResponse changePassword(@Valid ChangePasswordCommand command);
}
