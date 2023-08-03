package com.workoutly.application.user;

import com.workoutly.application.user.VO.UserId;
import com.workoutly.application.user.VO.UserRole;
import com.workoutly.application.user.VO.UserSnapshot;
import com.workoutly.application.user.dto.command.ActivationUserCommand;
import com.workoutly.application.user.dto.command.RegisterUserCommand;
import com.workoutly.application.user.dto.response.MessageResponse;
import com.workoutly.application.user.dto.response.RegisterUserResponse;
import com.workoutly.application.user.event.UserActivatedEvent;
import com.workoutly.application.user.event.UserCreatedEvent;
import com.workoutly.application.user.mapper.UserDataMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static com.workoutly.application.user.builder.RegisterUserCommandBuilder.aRegisterUserCommand;
import static com.workoutly.application.user.utils.TestUtils.mapToString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserApplicationServiceImplTest {

    @Mock
    private UserCommandHandler handler;
    @Mock
    private UserDataMapper mapper;
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
    }

    private void assertResponseIsValid(RegisterUserCommand command, RegisterUserResponse response) {
        RegisterUserResponse responseFromCommand = validCreatedResponseMessage(command);
        assertEquals(mapToString(responseFromCommand), mapToString(response));
    }

    private void assertResponseIsValid(UserActivatedEvent event, MessageResponse response) {
        String template = String.format("User: %s has been activated", event.getSnapshot().getUsername());
        assertTrue(response.getMessage().equals(template));
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
                false
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
                true
        );

        return new UserActivatedEvent(snapshot);
    }

    private RegisterUserResponse createRegisterUserResponse(RegisterUserCommand command) {
        return new RegisterUserResponse(
                "User created successfully, check your e-mail address to activate account.",
                command.getUsername()
        );
    }
}
