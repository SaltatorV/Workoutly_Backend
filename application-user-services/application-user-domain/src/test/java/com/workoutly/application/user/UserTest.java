package com.workoutly.application.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserTest {

    @Test
    public void testInitializeUser() {
        //given
        User user = new User("User", "Pa$$word", "example@example.pl", UserRole.User, false);

        //when
        user.initialize();

        //then

        Assertions.assertNotNull(user.getId());
    }
}
