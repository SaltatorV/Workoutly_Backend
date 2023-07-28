package com.workoutly.application.user;

import com.workoutly.application.user.VO.UserId;
import com.workoutly.application.user.VO.UserRole;
import com.workoutly.application.user.VO.UserSnapshot;
import com.workoutly.application.user.dto.command.RegisterUserCommand;
import com.workoutly.application.user.dto.response.RegisterUserResponse;
import com.workoutly.application.user.event.UserCreatedEvent;
import com.workoutly.application.user.mapper.UserDataMapper;
import jakarta.validation.ConstraintViolationException;
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
        verify(handler, times(1)).createCommonUser(command);
        verify(mapper, times(1)).userCreatedEventToRegisterUserResponse(userCreatedEvent);
    }

    private void assertResponseIsValid(RegisterUserCommand command, RegisterUserResponse response) {
        RegisterUserResponse responseFromCommand = validCreatedResponseMessage(command);
        assertEquals(mapToString(responseFromCommand), mapToString(response));
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
                UserRole.COMMON,
                false
        );

        return new UserCreatedEvent(userSnapshot);
    }

    private RegisterUserResponse createRegisterUserResponse(RegisterUserCommand command) {
        return new RegisterUserResponse(
                "User created successfully, check your e-mail address to activate account.",
                command.getUsername()
        );
    }
}
