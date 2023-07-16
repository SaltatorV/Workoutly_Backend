package com.workoutly.application.user;

import com.workoutly.application.user.VO.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserTest {

    @Test
    public void testInitializeUser() {
        //given
        User user = new User("User", "Pa$$word", "example@example.pl", UserRole.COMMON, false);

        //when
        user.initialize();

        //then
        assertNotNull(user.getId());
    }
}
