package com.workoutly.application.user;

import com.workoutly.application.user.VO.UserSnapshot;
import com.workoutly.application.user.dto.command.RegisterUserCommand;
import com.workoutly.application.user.event.UserCreatedEvent;
import com.workoutly.application.user.exception.UserNotRegisteredException;
import com.workoutly.application.user.exception.UserNotUniqueException;
import com.workoutly.application.user.mapper.UserDataMapper;
import com.workoutly.application.user.port.output.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
class UserCommandHandler {
    private final UserDataMapper userDataMapper;
    private final UserDomainService userDomainService;
    private final UserRepository userRepository;

    @Transactional
    public UserCreatedEvent createCommonUser(RegisterUserCommand registerUserCommand) {
        User user = userDataMapper.registerUserCommandToCommonUser(registerUserCommand);

        if(!userRepository.checkUserUniqueness(user.createSnapshot())) {
            throw new UserNotUniqueException();
        }

        UserCreatedEvent event = userDomainService.initializeUser(user);
        UserSnapshot savedSnapshot = userRepository.save(event.getSnapshot());

        if (savedSnapshot == null) {
            throw new UserNotRegisteredException();
        }
        return event;
    }
}
