package com.workoutly.application.user;

import com.workoutly.application.user.VO.UserRole;
import com.workoutly.application.user.dto.command.RegisterUserCommand;

import com.workoutly.application.user.event.UserCreatedEvent;
import com.workoutly.application.user.mapper.UserDataMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {UserCommandHandler.class})
public class UserCommandHandlerTest {

    @MockBean
    private UserDataMapper userDataMapper;

    @MockBean
    private UserDomainService userDomainService;

    @Autowired
    private UserCommandHandler userCommandHandler;

    @Test
    public void createUserTest() {
        //given
        var command = new RegisterUserCommand("Username",
                "Example@example.pl",
                "Password",
                "Password");

        doReturn(createCommonUserBasedOnCommand(command))
                .when(userDataMapper)
                .registerUserCommandToCommonUser(command);

        doReturn(createUserCreatedEventBasedOnCommand(command))
                .when(userDomainService)
                .initializeUser(any());

        //when
        UserCreatedEvent event = userCommandHandler.createCommonUser(command);

        //then
        assertIsEventCreated(event);
    }

    private User createCommonUserBasedOnCommand(RegisterUserCommand command) {
        return new User(
                command.getUsername(),
                command.getPassword(),
                command.getEmail(),
                UserRole.COMMON
        );
    }

    private UserCreatedEvent createUserCreatedEventBasedOnCommand(RegisterUserCommand command) {
        User user = createCommonUserBasedOnCommand(command);
        return new UserCreatedEvent(user.createSnapshot());
    }

    private void assertIsEventCreated(UserCreatedEvent event) {
        assertNotNull(event);
        assertNotNull(event.getSnapshot());
    }
}
