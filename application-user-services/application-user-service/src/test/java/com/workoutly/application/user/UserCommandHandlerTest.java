package com.workoutly.application.user;

import com.workoutly.application.user.VO.UserRole;
import com.workoutly.application.user.VO.UserSnapshot;
import com.workoutly.application.user.dto.command.RegisterUserCommand;

import com.workoutly.application.user.event.UserCreatedEvent;
import com.workoutly.application.user.exception.ApplicationUserDomainException;
import com.workoutly.application.user.exception.UserNotRegisteredException;
import com.workoutly.application.user.mapper.UserDataMapper;
import com.workoutly.application.user.port.output.UserRepository;
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
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserCommandHandler userCommandHandler;

    @Test
    public void testCreateCommonUser() {
        //given
        var command = aRegisterUserCommand()
                .withUsername("Test")
                .withPassword("Password")
                .withConfirmPassword("Password")
                .withEmailAddress("Example@example.pl")
                .create();

        var commonUser = createCommonUserBasedOnCommand(command);
        var event = createUserCreatedEventBasedOnCommand(command);
        doReturn(commonUser)
                .when(userDataMapper)
                .registerUserCommandToCommonUser(command);

        doReturn(true)
                .when(userRepository)
                .checkUserUniqueness(commonUser.createSnapshot());

        doReturn(event)
                .when(userDomainService)
                .initializeUser(commonUser);

        doReturn(event.getSnapshot())
                .when(userRepository)
                .save(event.getSnapshot());


        //when
        UserCreatedEvent createdEvent = userCommandHandler.createCommonUser(command);

        //then
        assertIsEventCreated(createdEvent);
        assertIsSnapshotValid(createdEvent, command);
    }

    @Test
    public void testCreateNewUserThrowException() {
        //given
        var command = aRegisterUserCommand()
                .withUsername("Test")
                .withPassword("Password")
                .withConfirmPassword("Password")
                .withEmailAddress("Example@example.pl")
                .create();

        var commonUser = createCommonUserBasedOnCommand(command);
        var event = createUserCreatedEventBasedOnCommand(command);

        doReturn(commonUser)
                .when(userDataMapper)
                .registerUserCommandToCommonUser(command);

        doReturn(true)
                .when(userRepository)
                .checkUserUniqueness(commonUser.createSnapshot());

        doReturn(event)
                .when(userDomainService)
                .initializeUser(commonUser);

        doReturn(null)
                .when(userRepository)
                .save(event.getSnapshot());

        //when
        var exception = throwExceptionWhenCreateUser(command);

        //then
        assertExceptionIsUserNotRegistred(exception);
    }

    @Test
    public void testUserIsNotUnique() {
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

        doReturn(false)
                .when(userRepository)
                .checkUserUniqueness(commonUser.createSnapshot());

        //when
        var exception = throwExceptionWhenUserIsNotUnique(command);

        //then
        assertExceptionIsUserNotUnique(exception);

    }

    private User createCommonUserBasedOnCommand(RegisterUserCommand command) {
        return new User(
                command.getUsername(),
                command.getPassword(),
                command.getEmail(),
                UserRole.COMMON_USER
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
        User user = new User(command.getUsername(), command.getPassword(), command.getEmail(), UserRole.COMMON_USER);
        user.setId(snapshot.getUserId());
        return user.createSnapshot();
    }

    private ApplicationUserDomainException throwExceptionWhenCreateUser(RegisterUserCommand command) {
        return assertThrows(UserNotRegisteredException.class, () -> userCommandHandler.createCommonUser(command));
    }


    private void assertExceptionIsUserNotRegistred(ApplicationUserDomainException exception) {
        assertEquals(new UserNotRegisteredException().getMessage(), exception.getMessage());
    }


    private void throwExceptionWhenUserIsNotUnique(RegisterUserCommand command) {
        return assertThrows(UserNotUniqueException.class, () -> userCommandHandler.createCommonUser(command));
    }

    private void assertExceptionIsUserNotUnique(UserNotUniqueException exception) {
        assertEquals(new UserNotRegisteredException().getMessage(), exception.getMessage());
    }
}
