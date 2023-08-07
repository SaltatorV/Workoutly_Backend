package com.workoutly.application.user;

import com.workoutly.application.user.dto.command.*;
import com.workoutly.application.user.dto.response.MessageResponse;
import com.workoutly.application.user.dto.response.RegisterUserResponse;
import com.workoutly.application.user.dto.response.TokenResponse;
import com.workoutly.application.user.event.UserActivatedEvent;
import com.workoutly.application.user.event.UserChangedEvent;
import com.workoutly.application.user.event.UserCreatedEvent;
import com.workoutly.application.user.mapper.UserDataMapper;
import com.workoutly.application.user.port.input.UserApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class UserApplicationServiceImpl implements UserApplicationService {

    private final UserCommandHandler handler;
    private final UserDataMapper mapper;

    @Override
    public RegisterUserResponse createCommonUser(RegisterUserCommand registerUserCommand) {
        UserCreatedEvent event = handler.createCommonUser(registerUserCommand);
        return mapper.userCreatedEventToRegisterUserResponse(event);
    }

    @Override
    public MessageResponse activateUserAccount(ActivationUserCommand activationUserCommand) {
        UserActivatedEvent event = handler.activateUser(activationUserCommand);
        return new MessageResponse(String.format("User: %s has been activated", event.getSnapshot().getUsername()));
    }

    @Override
    public TokenResponse authenticate(AuthenticationCommand authenticationCommand) {
        String token = handler.authenticate(authenticationCommand);
        return new TokenResponse(token);
    }

    @Override
    public MessageResponse changeEmail(ChangeEmailCommand command) {
        UserChangedEvent event = handler.changeEmail(command);
        return new MessageResponse("The email address has been changed.");
    }

    @Override
    public MessageResponse changePassword(ChangePasswordCommand command) {
        UserChangedEvent event = handler.changePassword(command);
        return new MessageResponse("The password has been changed.");
    }
}
