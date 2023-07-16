package com.workoutly.application.user;

import com.workoutly.application.user.VO.EmailAddress;
import com.workoutly.application.user.VO.Password;
import com.workoutly.application.user.VO.UserRole;
import com.workoutly.application.user.VO.Username;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void testInitializeUser() {
        //given
        User user = createUser()
                .withUsername("Test")
                .withPassword("Pa$$word")
                .withEmail("example@example.com")
                .withRole(UserRole.COMMON)
                .build();

        //when
        user.initialize();

        //then
        assertUserIsInitialized(user);
    }

    @Test
    public void testEnableUser() {
        //given
        User user = createUser()
                .withUsername("Test")
                .withPassword("Pa$$word")
                .withEmail("example@example.com")
                .withRole(UserRole.COMMON)
                .buildInitialized();

        //when
        user.enableUser();

        //then
        assertUserIsEnabled(user);
    }

    @Test
    public void testChangePassword() {
        //given
        User user = createUser()
                .withUsername("Test")
                .withPassword("Pa$$word")
                .withEmail("example@example.com")
                .withRole(UserRole.COMMON)
                .buildInitialized();

        //when
        user.changePassword("test");

        //then
        assertEquals(user.getPassword().getValue(), "test");
    }

    @Test
    public void testChangeEmail() {
        //given
        User user = createUser()
                .withUsername("Test")
                .withPassword("Pa$$word")
                .withEmail("example@example.com")
                .withRole(UserRole.COMMON)
                .buildInitialized();

        //when
        user.changeEmail("test@test.com");

        //then
        assertEquals(user.getEmail().getValue(), "test@test.com");
    }

    private void assertUserIsInitialized(User user) {
        assertNotNull(user.getId());
        assertFalse(user.isEnabled());
    }

    private void assertUserIsEnabled(User user) {
        assertTrue(user.isEnabled());
    }



    private UserBuilder createUser() {
        return new UserBuilder();
    }

    private class UserBuilder {
        Username username;
        Password password;
        EmailAddress email;
        UserRole role;

        UserBuilder withUsername(String username) {
            this.username = new Username(username);
            return this;
        }

        UserBuilder withPassword(String password) {
            this.password = new Password(password);
            return this;
        }

        UserBuilder withEmail(String email) {
            this.email = new EmailAddress(email);
            return this;
        }

        UserBuilder withRole(UserRole role) {
            this.role = role;
            return this;
        }

        User build() {
            return new User(username, password, email, role);
        }

        User buildInitialized() {
            User user = new User(username, password, email, role);
            user.enableUser();
            return user;
        }

    }
}
