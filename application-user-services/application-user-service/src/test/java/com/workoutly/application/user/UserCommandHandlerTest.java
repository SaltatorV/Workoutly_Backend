package com.workoutly.application.user;

import com.workoutly.application.user.VO.UserRole;
import com.workoutly.application.user.VO.UserSnapshot;
import com.workoutly.application.user.dto.command.RegisterUserCommand;

import com.workoutly.application.user.event.UserCreatedEvent;
import com.workoutly.application.user.mapper.UserDataMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.workoutly.application.user.builder.RegisterUserCommandBuilder.aRegisterUserCommand;
import static com.workoutly.application.user.utils.TestUtils.mapToString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class UserCommandHandlerTest {

    @Mock
    private UserDataMapper userDataMapper;

    @Mock
    private UserDomainService userDomainService;

    @InjectMocks
    private UserCommandHandler userCommandHandler;

    @Test
    public void createUserTest() {
        //given
        var command = aRegisterUserCommand()
                .withUsername("Test")
                .withPassword("Password")
                .withConfirmPassword("Password")
                .withEmailAddress("Example@example.pl")
                .create();

        var commonUser = createCommonUserBasedOnCommand(command);

        doReturn(commonUser)
                .when(userDataMapper)
                .registerUserCommandToCommonUser(command);

        doReturn(createUserCreatedEventBasedOnCommand(command))
                .when(userDomainService)
                .initializeUser(commonUser);

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
        UserSnapshot commandSnapshot = createCommonUserSnapshot(event.getSnapshot(), command);

        assertEquals(mapToString(commandSnapshot), mapToString(snapshot));
    }

    private UserSnapshot createCommonUserSnapshot(UserSnapshot snapshot, RegisterUserCommand command) {
        User user = new User(command.getUsername(), command.getPassword(), command.getEmail(), UserRole.COMMON);
        user.setId(snapshot.getUserId());
        return user.createSnapshot();
    }
}
