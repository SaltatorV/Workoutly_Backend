package com.workoutly.application.user;

import com.workoutly.application.user.VO.*;
import com.workoutly.application.user.dto.command.ActivationUserCommand;
import com.workoutly.application.user.dto.command.RegisterUserCommand;

import com.workoutly.application.user.event.UserActivatedEvent;
import com.workoutly.application.user.event.UserCreatedEvent;
import com.workoutly.application.user.exception.ApplicationUserDomainException;
import com.workoutly.application.user.exception.UserNotBoundException;
import com.workoutly.application.user.exception.UserNotRegisteredException;
import com.workoutly.application.user.exception.UserNotUniqueException;
import com.workoutly.application.user.mapper.UserDataMapper;
import com.workoutly.application.user.port.output.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

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

        doReturn(false)
                .when(userRepository)
                .checkUserExists(commonUser.createSnapshot());

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

        doReturn(false)
                .when(userRepository)
                .checkUserExists(commonUser.createSnapshot());

        doReturn(event)
                .when(userDomainService)
                .initializeUser(commonUser);

        //when
        var exception = throwExceptionWhenCreateUser(command);

        //then
        assertExceptionIsUserNotRegistered(exception);
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

        doReturn(true)
                .when(userRepository)
                .checkUserExists(commonUser.createSnapshot());

        //when
        var exception = throwExceptionWhenUserIsNotUnique(command);

        //then
        assertExceptionIsUserNotUnique(exception);

    }

    @Test
    public void testUserActivated() {
        //given
        var activationUserCommand = new ActivationUserCommand("abcdefgh");
        var snapshot = createCommonUserSnapshot();

        doReturn(Optional.of(snapshot))
                .when(userRepository)
                .findByVerificationToken(activationUserCommand.getToken());

        doReturn(createActivatedUserEvent(snapshot))
                .when(userDomainService)
                .activateUser(User.restore(snapshot));

        //when
        var event = userCommandHandler.activateUser(activationUserCommand);

        //then
        assertIsEventCreated(event);
        assertIsUserActivated(event);
    }

    @Test
    public void testUserActivationThrowNotBoundException() {
        //given
        var command = new ActivationUserCommand("abcdefgh");

        doReturn(Optional.empty())
                .when(userRepository)
                .findByVerificationToken(command.getToken());

        //when
        var exception = throwExceptionWhenUserIsNotBound(command);

        //then
        assertExceptionIsUserNotBound(exception);
    }

    private User createCommonUserBasedOnCommand(RegisterUserCommand command) {
        return new User(
                command.getUsername(),
                command.getPassword(),
                command.getEmail(),
                UserRole.COMMON_USER
        );
    }

    private UserSnapshot createCommonUserSnapshot() {
        return new UserSnapshot(
                new UserId(UUID.randomUUID()),
                "test",
                "password",
                "example@example.to",
                UserRole.COMMON_USER,
                false,
                createTokenSnapshot()
        );
    }

    private UserActivatedEvent createActivatedUserEvent(UserSnapshot snapshot) {
        UserSnapshot activated = new UserSnapshot(
                snapshot.getUserId(),
                snapshot.getUsername(),
                snapshot.getEmail(),
                snapshot.getPassword(),
                snapshot.getRole(),
                true,
                snapshot.getToken()
        );

        return new UserActivatedEvent(activated);
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

    private void assertIsEventCreated(UserActivatedEvent event) {
        assertNotNull(event);
        assertNotNull(event.getSnapshot());
    }

    private void assertIsSnapshotValid(UserCreatedEvent event, RegisterUserCommand command) {
        UserSnapshot snapshot = event.getSnapshot();
        UserSnapshot commandSnapshot = createCommonUserSnapshot(event.getSnapshot(), command);

        assertEquals(mapToString(commandSnapshot), mapToString(snapshot));
    }

    private void assertIsUserActivated(UserActivatedEvent event) {
        assertTrue(event.getSnapshot().isEnabled());
    }

    private UserSnapshot createCommonUserSnapshot(UserSnapshot snapshot, RegisterUserCommand command) {
        User user = new User(command.getUsername(), command.getPassword(), command.getEmail(), UserRole.COMMON_USER);
        user.setId(snapshot.getUserId());
        return user.createSnapshot();
    }

    private ApplicationUserDomainException throwExceptionWhenCreateUser(RegisterUserCommand command) {
        return assertThrows(UserNotRegisteredException.class, () -> userCommandHandler.createCommonUser(command));
    }


    private void assertExceptionIsUserNotUnique(ApplicationUserDomainException exception) {
        assertExceptionsMessagesEqual(new UserNotUniqueException(), exception);
    }

    private void assertExceptionIsUserNotRegistered(ApplicationUserDomainException exception) {
        assertExceptionsMessagesEqual(new UserNotRegisteredException(), exception);
    }

    private void assertExceptionIsUserNotBound(ApplicationUserDomainException exception) {
        assertExceptionsMessagesEqual(new UserNotBoundException(), exception);
    }

    private void assertExceptionsMessagesEqual(ApplicationUserDomainException expected, ApplicationUserDomainException actual) {
        assertEquals(expected.getMessage(), actual.getMessage());
    }

    private ApplicationUserDomainException throwExceptionWhenUserIsNotUnique(RegisterUserCommand command) {
        return assertThrows(UserNotUniqueException.class, () -> userCommandHandler.createCommonUser(command));
    }

    private UserNotBoundException throwExceptionWhenUserIsNotBound(ActivationUserCommand command) {
        return assertThrows(UserNotBoundException.class, () -> userCommandHandler.activateUser(command));
    }

    private VerificationTokenSnapshot createTokenSnapshot() {
        return new VerificationTokenSnapshot(new TokenId(UUID.randomUUID()),UUID.randomUUID().toString(), Date.from(Instant.now() ));
    }
}
