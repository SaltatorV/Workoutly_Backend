package com.workoutly.application.user;

import com.workoutly.application.user.VO.*;
import com.workoutly.application.user.dto.command.*;
import com.workoutly.application.user.dto.response.MessageResponse;
import com.workoutly.application.user.dto.response.RegisterUserResponse;
import com.workoutly.application.user.event.UserActivatedEvent;
import com.workoutly.application.user.event.UserUpdatedEvent;
import com.workoutly.application.user.event.UserCreatedEvent;
import com.workoutly.application.user.mapper.UserDataMapper;
import com.workoutly.application.user.port.output.UserEventPublisher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.time.Instant;
import java.util.UUID;

import static com.workoutly.application.user.builder.RegisterUserCommandBuilder.aRegisterUserCommand;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserApplicationServiceImplTest {

    @Mock
    private UserCommandHandler handler;
    @Mock
    private UserDataMapper mapper;
    @Mock
    private UserEventPublisher publisher;
    @InjectMocks
    private UserApplicationServiceImpl service;


    @Test
    public void testCreateCommonUser() {
        //given
        var command = aRegisterUserCommand()
                .withUsername("example")
                .withEmailAddress("example@mail.to")
                .withPassword("Sup3rS3cureP@@s")
                .withConfirmPassword("Sup3rS3cureP@@s")
                .create();

        var userCreatedEvent = userCreatedEventBasedOnRequest(command);

        doReturn(userCreatedEvent)
                .when(handler).createCommonUser(command);

        doReturn(createRegisterUserResponse(command))
                .when(mapper).userCreatedEventToRegisterUserResponse(userCreatedEvent);


        //when
        var response = service.createCommonUser(command);

        //then
        assertResponseIsValid(command, response);
        verify(publisher, times(1)).publish(userCreatedEvent);
    }

    @Test
    public void testActivateUserAccount() {
        //given
        var command = new ActivationUserCommand("abcdefgh");
        var userActivatedEvent = createUserActivatedEvent();

        doReturn(userActivatedEvent)
                .when(handler)
                .activateUser(command);

        //when
        var response = service.activateUserAccount(command);

        //then
        assertResponseIsValid(userActivatedEvent, response);
        verify(publisher, times(1)).publish(userActivatedEvent);
    }

    @Test
    public void testAuthenticateUser() {
        //given
        var command = new AuthenticationCommand("test", "Super$ecure5");
        var token = createRandomToken();

        doReturn(token)
                .when(handler)
                .authenticate(command);

        //when
        var response = service.authenticate(command);

        //then
        assertEquals(token, response.getToken());
    }

    @Test
    public void testChangeEmail() {
        //given
        var command = new ChangeEmailCommand("example@example.to", "password");
        var event = createUserChangedEmailEvent(command);

        doReturn(event)
                .when(handler)
                .changeEmail(command);

        //when
        var response = service.changeEmail(command);

        //then
        assertResponseIsEmailChanged(response);
        verify(publisher, times(1)).publish(event);
    }


    @Test
    public void testChangePassword() {
        //given
        var command = new ChangePasswordCommand("password", "new-password");
        var event = createUserChangedPasswordEvent(command);

        doReturn(event)
                .when(handler)
                .changePassword(command);

        //when
        var response = service.changePassword(command);

        //then
        assertResponseIsPasswordChanged(response);
        verify(publisher, times(1)).publish(event);
    }


    private void assertResponseIsValid(RegisterUserCommand command, RegisterUserResponse response) {
        RegisterUserResponse responseFromCommand = validCreatedResponseMessage(command);
        assertEquals(responseFromCommand, response);
    }

    private void assertResponseIsValid(UserActivatedEvent event, MessageResponse response) {
        String template = String.format("User: %s has been activated", event.getSnapshot().getUsername());
        assertEquals(response.getMessage(), template);
    }

    private void assertResponseIsEmailChanged(MessageResponse response) {
        String template = "The email address has been changed.";
        assertEquals(response.getMessage(), template);
    }

    private void assertResponseIsPasswordChanged(MessageResponse response) {
        String template = "The password has been changed.";
        assertEquals(response.getMessage(), template);
    }

    private RegisterUserResponse validCreatedResponseMessage(RegisterUserCommand command) {
        return new RegisterUserResponse(
                "User created successfully, check your e-mail address to activate account.",
                command.getUsername()
        );
    }

    private UserCreatedEvent userCreatedEventBasedOnRequest(RegisterUserCommand command) {
        UserSnapshot userSnapshot = new UserSnapshot(new UserId(UUID.randomUUID()),
                command.getUsername(),
                command.getEmail(),
                command.getPassword(),
                UserRole.COMMON_USER,
                false,
                null
        );

        return new UserCreatedEvent(userSnapshot);
    }


    private UserActivatedEvent createUserActivatedEvent() {
        UserSnapshot snapshot = new UserSnapshot(
                new UserId(UUID.randomUUID()),
                "test",
                "example@example.to",
                "password",
                UserRole.COMMON_USER,
                true,
                createTokenSnapshot()
        );

        return new UserActivatedEvent(snapshot);
    }

    private RegisterUserResponse createRegisterUserResponse(RegisterUserCommand command) {
        return new RegisterUserResponse(
                "User created successfully, check your e-mail address to activate account.",
                command.getUsername()
        );
    }

    private VerificationTokenSnapshot createTokenSnapshot() {
        return new VerificationTokenSnapshot(new TokenId(UUID.randomUUID()),UUID.randomUUID().toString(), Date.from(Instant.now() ));
    }

    private String createRandomToken() {
        return UUID.randomUUID().toString();
    }


    private UserUpdatedEvent createUserChangedEmailEvent(ChangeEmailCommand command) {
        UserSnapshot userSnapshot = new UserSnapshot(new UserId(UUID.randomUUID()),
                "test",
                command.getEmailAddress(),
                "password",
                UserRole.COMMON_USER,
                true,
                null
        );

        return new UserUpdatedEvent(userSnapshot, "The email address has been changed.");
    }

    private UserUpdatedEvent createUserChangedPasswordEvent(ChangePasswordCommand command) {
        UserSnapshot userSnapshot = new UserSnapshot(new UserId(UUID.randomUUID()),
                "test",
                "example@example.to",
                command.getNewPassword(),
                UserRole.COMMON_USER,
                true,
                null
        );

        return new UserUpdatedEvent(userSnapshot, "The password has been changed.");
    }
}
