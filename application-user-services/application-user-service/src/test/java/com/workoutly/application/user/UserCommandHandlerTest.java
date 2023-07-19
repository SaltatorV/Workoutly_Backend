package com.workoutly.application.user;

import org.junit.jupiter.api.Test;

public class UserCommandHandlerTest {

    private UserCommandHandler userCommandHandler = new UserCommandHandler();

    @Test
    public void createUserTest() {
        //given
        RegisterUserCommand= new RegisterUserCommand("Username", "Example@example.pl", "Password", "Password");

        //when
        UserCreatedEvent event = userCommandHandler.createUser(request);

        //then

    }
}
