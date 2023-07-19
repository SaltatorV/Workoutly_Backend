package com.workoutly.application.user;

import org.junit.jupiter.api.Test;

public class UserCommandHandlerTest {

    private UserCommandHandler userCommandHandler = new UserCommandHandler();

    @Test
    public void createUserTest() {
        //given
        RegisterUserRequest request = new RegisterUserRequest("Username", "Example@example.pl", "Password", "Password");

        //when
        UserCreatedEvent event = userCommandHandler.createUser(request);

        //then

    }
}
