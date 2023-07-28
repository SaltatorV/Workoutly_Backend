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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static com.workoutly.application.user.builder.RegisterUserCommandBuilder.aRegisterUserCommand;
import static com.workoutly.application.user.utils.TestUtils.mapToString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ValidationAutoConfiguration.class})
@SpringBootTest(classes = {UserApplicationServiceImpl.class})
public class UserApplicationServiceImplTest {

    @MockBean
    private UserCommandHandler userCommandHandler;
    @MockBean
    private UserDataMapper userDataMapper;

    @Autowired
    private UserApplicationServiceImpl userApplicationService;

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
                .when(userCommandHandler).createCommonUser(command);

        doReturn(createRegisterUserResponse(command))
                .when(userDataMapper).userCreatedEventToRegisterUserResponse(userCreatedEvent);


        //when
        var response = userApplicationService.createCommonUser(command);

        //then
        assertResponseIsValid(command, response);
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

    private ConstraintViolationException commandThrowsConstraintViolationException(RegisterUserCommand command) {
        return assertThrows(ConstraintViolationException.class, () -> userApplicationService.createCommonUser(command));
    }

    private void assertExceptionMessageIsEmptyViolation(ConstraintViolationException exception) {
        assertTrue(exception.getMessage().contains("Field cannot be empty"));
    }
}
