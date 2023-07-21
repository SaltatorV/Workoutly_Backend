package com.workoutly.application.user;

import com.workoutly.application.user.VO.UserRole;
import com.workoutly.application.user.VO.UserSnapshot;
import com.workoutly.application.user.dto.command.RegisterUserCommand;

import com.workoutly.application.user.event.UserCreatedEvent;
import com.workoutly.application.user.mapper.UserDataMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.workoutly.application.user.builder.RegisterUserCommandBuilder.anRegisterUserCommand;
import static org.junit.jupiter.api.Assertions.*;
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
        var command = anRegisterUserCommand()
                .withUsername("Test")
                .withPassword("Password")
                .withConfirmPassword("Password")
                .withEmailAddress("Example@example.pl")
                .create();

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
        assertIsSnapshotValid(event, command);
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
        user.initialize();
        return new UserCreatedEvent(user.createSnapshot());
    }

    private void assertIsEventCreated(UserCreatedEvent event) {
        assertNotNull(event);
        assertNotNull(event.getSnapshot());
    }

    private void assertIsSnapshotValid(UserCreatedEvent event, RegisterUserCommand command) {
        UserSnapshot snapshot = event.getSnapshot();
        assertNotNull(snapshot.getUserId());
        assertFalse(snapshot.isEnabled());
        assertEquals(command.getUsername(), snapshot.getUsername());
        assertEquals(command.getPassword(), snapshot.getPassword());
        assertEquals(command.getEmail(), snapshot.getEmail());
    }
}
