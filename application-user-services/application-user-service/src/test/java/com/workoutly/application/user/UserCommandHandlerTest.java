package com.workoutly.application.user;

import com.workoutly.application.user.dto.command.RegisterUserCommand;
import org.junit.jupiter.api.Assertions;
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
        UserCreatedEvent event = userCommandHandler.createUser(request);

        //then
        assertNotNull(event.getUser());
    }
}
