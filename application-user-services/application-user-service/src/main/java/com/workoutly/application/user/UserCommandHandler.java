package com.workoutly.application.user;

import com.workoutly.application.user.dto.command.RegisterUserCommand;
import com.workoutly.application.user.event.UserCreatedEvent;
import com.workoutly.application.user.mapper.UserDataMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
class UserCommandHandler {
    private final UserDataMapper userDataMapper;
    private final UserDomainService userDomainService;

    @Transactional
    public UserCreatedEvent createCommonUser(RegisterUserCommand registerUserCommand) {
        User user = userDataMapper.registerUserCommandToCommonUser(registerUserCommand);
        UserCreatedEvent event = userDomainService.initializeUser(user);

        return event;
    }
}
