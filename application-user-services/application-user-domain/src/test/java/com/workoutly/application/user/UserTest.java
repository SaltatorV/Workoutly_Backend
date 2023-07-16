package com.workoutly.application.user;

import com.workoutly.application.user.VO.UserRole;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserTest {

    @Test
    public void testInitializeUser() {
        //given
        User user = new User("User", "Pa$$word", "example@example.pl", UserRole.COMMON);

        //when
        user.initialize();

        //then
        assertNotNull(user.getId());
        assertFalse(user.isEnabled());
    }
}
