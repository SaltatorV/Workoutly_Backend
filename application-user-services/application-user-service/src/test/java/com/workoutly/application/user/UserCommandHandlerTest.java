package com.workoutly.application.user;

import com.workoutly.application.user.dto.command.RegisterUserCommand;

import com.workoutly.application.user.event.UserCreatedEvent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserCommandHandlerTest {

    private UserCommandHandler userCommandHandler = new UserCommandHandler();

    @Test
    public void createUserTest() {
        //given
        RegisterUserCommand registerUserCommand = new RegisterUserCommand("Username",
                "Example@example.pl",
                "Password",
                "Password");

        //when
        UserCreatedEvent event = userCommandHandler.createCommonUser(registerUserCommand);

        //then
        assertNotNull(event.getSnapshot());
    }
}
