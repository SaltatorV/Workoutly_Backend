package com.workoutly.application.user;

import com.workoutly.application.user.dto.command.RegisterUserCommand;
import com.workoutly.application.user.dto.response.RegisterUserResponse;
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
}
