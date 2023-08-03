package com.workoutly.application.user;

import com.workoutly.application.user.VO.UserSnapshot;
import com.workoutly.application.user.dto.command.ActivationUserCommand;
import com.workoutly.application.user.dto.command.RegisterUserCommand;
import com.workoutly.application.user.event.UserActivatedEvent;
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

        checkUserIsUnique(user.createSnapshot());

        UserCreatedEvent event = userDomainService.initializeUser(user);
        UserSnapshot savedSnapshot = userRepository.save(event.getSnapshot());

        checkUserIsSaved(savedSnapshot);

        return event;
    }

    @Transactional
    public UserActivatedEvent activateUser(ActivationUserCommand command) {
        return null;
    }

    private void checkUserIsUnique(UserSnapshot snapshot) {
        if(userRepository.checkUserExists(snapshot)) {
            throw new UserNotUniqueException();
        }
    }

    private void checkUserIsSaved(UserSnapshot snapshot) {
        if (snapshot == null) {
            throw new UserNotRegisteredException();
        }
    }
}
