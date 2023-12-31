package com.workoutly.application.user;

import com.workoutly.application.user.VO.*;
import com.workoutly.application.user.auth.AuthenticationProvider;
import com.workoutly.application.user.dto.command.ActivationUserCommand;
import com.workoutly.application.user.dto.command.ChangeEmailCommand;
import com.workoutly.application.user.dto.command.ChangePasswordCommand;
import com.workoutly.application.user.dto.command.RegisterUserCommand;

import com.workoutly.application.user.event.UserActivatedEvent;
import com.workoutly.application.user.event.UserCreatedEvent;
import com.workoutly.application.user.event.UserUpdatedEvent;
import com.workoutly.application.user.exception.*;
import com.workoutly.application.user.mapper.UserDataMapper;
import com.workoutly.application.user.port.output.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

import static com.workoutly.application.user.builder.RegisterUserCommandBuilder.aRegisterUserCommand;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserCommandHandlerTest {

    @Mock
    private UserDataMapper userDataMapper;
    @Mock
    private UserDomainService userDomainService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AuthenticationProvider authenticationProvider;

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
    public void testCreateNewUserThrowNotSavedException() {
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
        var exception = throwNotSavedExceptionWhenCreateUser(command);

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
        var snapshot = createValidUserSnapshot();
        var createdEvent = createActivatedUserEvent(snapshot);
        doReturn(Optional.of(snapshot))
                .when(userRepository)
                .findByVerificationToken(activationUserCommand.getToken());

        doReturn(createdEvent)
                .when(userDomainService)
                .activateUser(User.restore(snapshot));

        doReturn(createdEvent.getSnapshot())
                .when(userRepository)
                .save(createdEvent.getSnapshot());

        //when
        var event = userCommandHandler.activateUser(activationUserCommand);

        //then
        assertIsEventCreated(event);
        assertIsUserActivated(event);
        verify(userRepository, times(1)).save(any());
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

    @Test
    public void testUserActivationThrowTokenExpiredException() {
        //given
        var command = new ActivationUserCommand("abcdefgh");
        var snapshot = createExpiredUserSnapshot();

        doReturn(Optional.of(snapshot))
                .when(userRepository)
                .findByVerificationToken(command.getToken());

        //when
        var exception = throwExceptionWhenUserIsExpired(command);

        //then
        assertExceptionIsVerificationTokenExpired(exception);
    }

    @Test
    public void testChangeEmailAddress() {
        //given
        var command = new ChangeEmailCommand("email@email.to", "password");
        var snapshot = createValidUserSnapshot();
        var user = User.restore(snapshot);
        var createdEvent = createUserUpdateEvent(command, snapshot);

        doReturn(snapshot)
                .when(userRepository)
                .findByUsername(any());

        doReturn(true)
                .when(authenticationProvider)
                .checkPasswordsMatch(command.getPassword(), snapshot.getPassword());

        doReturn(createdEvent)
                .when(userDomainService)
                .changeEmail(user, command.getEmailAddress());

        //when
        var event = userCommandHandler.changeEmail(command);

        //then
        assertIsEventCreated(event);
        assertIsEmailChanged(event, command);
        verify(userRepository, times(1)).save(any());
    }

    @Test
    public void testChangeEmailAddressThrowError() {
        //given
        var command = new ChangeEmailCommand("email@email.to", "password");
        var snapshot = createValidUserSnapshot();

        doReturn(snapshot)
                .when(userRepository)
                .findByUsername(any());

        doReturn(false)
                .when(authenticationProvider)
                .checkPasswordsMatch(command.getPassword(), snapshot.getPassword());

        //when
        var exception = throwExceptionWhenChangeEmail(command);

        //then
        assertExceptionIsPasswordMismatch(exception);
    }

    @Test
    public void testChangePassword() {
        //given
        var command = new ChangePasswordCommand("password", "password");
        var encodedPassword = "AAAAAAAAAAAAAAAAAAA";
        var snapshot = createValidUserSnapshot();
        var user = User.restore(snapshot);
        var createdEvent = createUserUpdateEvent(encodedPassword, snapshot);

        doReturn(snapshot)
                .when(userRepository)
                .findByUsername(any());

        doReturn(true)
                .when(authenticationProvider)
                .checkPasswordsMatch(command.getPassword(), snapshot.getPassword());

        doReturn(encodedPassword)
                .when(authenticationProvider)
                .encodePassword(command.getNewPassword());

        doReturn(createdEvent)
                .when(userDomainService)
                .changePassword(user, encodedPassword);

        //when
        var event = userCommandHandler.changePassword(command);

        //then
        assertIsEventCreated(event);
        assertIsPasswordChanged(event, encodedPassword);
        verify(userRepository, times(1)).save(any());
    }

    @Test
    public void testChangePasswordThrowError() {
        //given
        var command = new ChangePasswordCommand("password", "password");
        var snapshot = createValidUserSnapshot();

        doReturn(snapshot)
                .when(userRepository)
                .findByUsername(any());

        doReturn(false)
                .when(authenticationProvider)
                .checkPasswordsMatch(command.getPassword(), snapshot.getPassword());

        //when
        var exception = throwExceptionWhenChangePassword(command);

        //then
        assertExceptionIsPasswordMismatch(exception);
    }


    private User createCommonUserBasedOnCommand(RegisterUserCommand command) {
        return new User(
                command.getUsername(),
                command.getPassword(),
                command.getEmail(),
                UserRole.COMMON_USER
        );
    }

    private UserSnapshot createValidUserSnapshot() {
        return createUserSnapshot(createTokenSnapshot());
    }

    private UserSnapshot createExpiredUserSnapshot() {
        return createUserSnapshot(createExpiredTokenSnapshot());
    }

    private UserSnapshot createUserSnapshot(VerificationTokenSnapshot tokenSnapshot) {
        return new UserSnapshot(
                new UserId(UUID.randomUUID()),
                "test",
                "password",
                "example@example.to",
                UserRole.COMMON_USER,
                false,
                tokenSnapshot
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

    private UserUpdatedEvent createUserUpdateEvent(ChangeEmailCommand command, UserSnapshot snapshot) {
        var newSnapshot = new UserSnapshot(
                snapshot.getUserId(),
                snapshot.getUsername(),
                command.getEmailAddress(),
                snapshot.getPassword(),
                snapshot.getRole(),
                snapshot.isEnabled(),
                snapshot.getToken()
        );
        return new UserUpdatedEvent(newSnapshot, "Your email address has been changed.");
    }


    private UserUpdatedEvent createUserUpdateEvent(String encodedPassword, UserSnapshot snapshot) {
        var newSnapshot = new UserSnapshot(
                snapshot.getUserId(),
                snapshot.getUsername(),
                snapshot.getEmail(),
                encodedPassword,
                snapshot.getRole(),
                snapshot.isEnabled(),
                snapshot.getToken()
        );
        return new UserUpdatedEvent(newSnapshot, "Your password has been changed.");
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

    private void assertIsEventCreated(UserUpdatedEvent event) {
        assertNotNull(event);
        assertNotNull(event.getSnapshot());
        assertNotNull(event.getMessage());
    }

    private void assertIsSnapshotValid(UserCreatedEvent event, RegisterUserCommand command) {
        UserSnapshot snapshot = event.getSnapshot();
        UserSnapshot commandSnapshot = createCommonUserSnapshot(event.getSnapshot(), command);

        assertEquals(commandSnapshot, snapshot);
    }

    private void assertIsUserActivated(UserActivatedEvent event) {
        assertTrue(event.getSnapshot().isEnabled());
    }


    private void assertIsEmailChanged(UserUpdatedEvent event, ChangeEmailCommand command) {
        assertEquals(command.getEmailAddress(), event.getSnapshot().getEmail());
    }

    private void assertIsPasswordChanged(UserUpdatedEvent event, String encodedPassword) {
        assertEquals(encodedPassword, event.getSnapshot().getPassword());
    }

    private UserSnapshot createCommonUserSnapshot(UserSnapshot snapshot, RegisterUserCommand command) {
        User user = new User(command.getUsername(), command.getPassword(), command.getEmail(), UserRole.COMMON_USER, VerificationToken.restore(snapshot.getToken()));
        user.setId(snapshot.getUserId());
        return user.createSnapshot();
    }

    private ApplicationUserDomainException throwNotSavedExceptionWhenCreateUser(RegisterUserCommand command) {
        return assertThrows(UserNotRegisteredException.class, () -> userCommandHandler.createCommonUser(command));
    }
    private ApplicationUserDomainException throwExceptionWhenUserIsExpired(ActivationUserCommand command) {
        return assertThrows(VerificationTokenExpiredException.class, () -> userCommandHandler.activateUser(command));
    }

    private ApplicationUserDomainException throwExceptionWhenChangeEmail(ChangeEmailCommand command) {
        return assertThrows(PasswordMismatchException.class, () -> userCommandHandler.changeEmail(command));
    }

    private ApplicationUserDomainException throwExceptionWhenChangePassword(ChangePasswordCommand command) {
        return assertThrows(PasswordMismatchException.class, () -> userCommandHandler.changePassword(command));
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

    private void assertExceptionIsVerificationTokenExpired(ApplicationUserDomainException exception) {
        assertExceptionsMessagesEqual(new VerificationTokenExpiredException(), exception);
    }

    private void assertExceptionIsPasswordMismatch(ApplicationUserDomainException exception) {
        assertExceptionsMessagesEqual(new PasswordMismatchException(), exception);
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
        return VerificationToken.generateToken().createTokenSnapshot();
    }

    private VerificationTokenSnapshot createExpiredTokenSnapshot() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(java.util.Date.from(Instant.now()));
        calendar.add(Calendar.HOUR_OF_DAY, -2);

        return new VerificationTokenSnapshot(new TokenId(UUID.randomUUID()),UUID.randomUUID().toString(), calendar.getTime());
    }


}
