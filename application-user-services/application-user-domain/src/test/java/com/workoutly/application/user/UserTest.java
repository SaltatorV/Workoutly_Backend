package com.workoutly.application.user;

import com.workoutly.application.user.VO.*;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.workoutly.application.user.utils.TestUtils.mapToString;
import static com.workoutly.application.user.utils.UserSnapshotBuilder.anUserSnapshot;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void testInitializeUser() {
        //given
        User user = anUser()
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
        User user = anUser()
                .withUsername("Test")
                .withPassword("Pa$$word")
                .withEmail("example@example.com")
                .withRole(UserRole.COMMON)
                .buildInitialized();

        //when
        user.enable();

        //then
        assertUserIsEnabled(user);
    }

    @Test
    public void testChangePassword() {
        //given
        User user = anUser()
                .withUsername("Test")
                .withPassword("Pa$$word")
                .withEmail("example@example.com")
                .withRole(UserRole.COMMON)
                .build();

        //when
        user.changePassword("test");

        //then
        assertEquals("test", user.createSnapshot().getPassword());
    }

    @Test
    public void testChangeEmail() {
        //given
        User user = anUser()
                .withUsername("Test")
                .withPassword("Pa$$word")
                .withEmail("example@example.com")
                .withRole(UserRole.COMMON)
                .build();

        //when
        user.changeEmail("test@test.com");

        //then
        assertEquals("test@test.com", user.createSnapshot().getEmail());
    }

    @Test
    public void testCreateUserSnapshot() {
        //given
        User user = anUser()
                .withUsername("Test")
                .withPassword("Pa$$word")
                .withEmail("example@example.com")
                .withRole(UserRole.COMMON)
                .build();

        UserSnapshot mockSnapshot = anUserSnapshot()
                .withUsername("Test")
                .withPassword("Pa$$word")
                .withEmail("example@example.com")
                .withRole(UserRole.COMMON)
                .isEnabled(false)
                .build();

        //when
        UserSnapshot snapshot = user.createSnapshot();

        //then
        assertSnapshotsAreEqual(snapshot, mockSnapshot);
    }

    @Test
    public void testCreateUserSnapshotWithInitializedUser() {
        //given
        User user = anUser()
                .withUsername("Test")
                .withPassword("Pa$$word")
                .withEmail("example@example.com")
                .withRole(UserRole.COMMON)
                .buildInitialized();

        UserSnapshot mockSnapshot = anUserSnapshot()
                .withId(user.getId())
                .withUsername("Test")
                .withPassword("Pa$$word")
                .withEmail("example@example.com")
                .withRole(UserRole.COMMON)
                .build();

        //when
        UserSnapshot snapshot = user.createSnapshot();

        //then
        assertSnapshotsAreEqual(snapshot, mockSnapshot);
    }

    @Test
    public void testCreateUserSnapshotWithEnabledUser() {
        //given
        User user = anUser()
                .withUsername("Test")
                .withPassword("Pa$$word")
                .withEmail("example@example.com")
                .withRole(UserRole.COMMON)
                .buildInitialized();

        user.enable();

        UserSnapshot mockSnapshot = anUserSnapshot()
                .withId(user.getId())
                .withUsername("Test")
                .withPassword("Pa$$word")
                .withEmail("example@example.com")
                .withRole(UserRole.COMMON)
                .isEnabled(true)
                .build();

        //when
        UserSnapshot snapshot = user.createSnapshot();

        //then
        assertSnapshotsAreEqual(snapshot, mockSnapshot);
    }

    @Test
    public void testRestoreUser() {
        //given
        UserSnapshot snapshot = anUserSnapshot()
                .withUsername("Test")
                .withPassword("Pa$$word")
                .withEmail("example@example.com")
                .withRole(UserRole.COMMON)
                .build();

        User mockUser = anUser()
                .withUsername("Test")
                .withPassword("Pa$$word")
                .withEmail("example@example.com")
                .withRole(UserRole.COMMON)
                .build();


        //when
        User user = User.restore(snapshot);

        //then
        assertUsersAreEqual(user, mockUser);
    }

    @Test
    public void testRestoreInitializedUser() {
        //given
        UserId userId = new UserId(UUID.randomUUID());

        UserSnapshot snapshot = anUserSnapshot()
                .withId(userId)
                .withUsername("Test")
                .withPassword("Pa$$word")
                .withEmail("example@example.com")
                .withRole(UserRole.COMMON)
                .build();

        User mockUser = anUser()
                .withUsername("Test")
                .withPassword("Pa$$word")
                .withEmail("example@example.com")
                .withRole(UserRole.COMMON)
                .build();

        mockUser.setId(userId);

        //when
        User user = User.restore(snapshot);

        //then
        assertUsersAreEqual(user, mockUser);
    }

    @Test
    public void testRestoreEnabledUser() {
        //given
        UserId userId = new UserId(UUID.randomUUID());

        UserSnapshot snapshot = anUserSnapshot()
                .withId(userId)
                .withUsername("Test")
                .withPassword("Pa$$word")
                .withEmail("example@example.com")
                .withRole(UserRole.COMMON)
                .isEnabled(true)
                .build();

        User mockUser = anUser()
                .withUsername("Test")
                .withPassword("Pa$$word")
                .withEmail("example@example.com")
                .withRole(UserRole.COMMON)
                .build();

        mockUser.setId(userId);
        mockUser.enable();

        //when
        User user = User.restore(snapshot);

        //then
        assertUsersAreEqual(user, mockUser);
    }


    private void assertUserIsInitialized(User user) {
        assertNotNull(user.getId());
        assertFalse(user.isEnabled());
    }

    private void assertUserIsEnabled(User user) {
        assertTrue(user.isEnabled());
    }

    private void assertSnapshotsAreEqual(UserSnapshot snapshot, UserSnapshot mockSnapshot) {
        assertEquals(mapToString(snapshot), mapToString(mockSnapshot));
    }

    private void assertUsersAreEqual(User user, User mockUser) {
        assertEquals(mapToString(user), mapToString(mockUser));
    }

    UserBuilder anUser() {
        return new UserBuilder();
    }

    private class UserBuilder {
        private Username username;
        private Password password;
        private EmailAddress email;
        private UserRole role;

        private UserBuilder withUsername(String username) {
            this.username = new Username(username);
            return this;
        }

        private UserBuilder withPassword(String password) {
            this.password = new Password(password);
            return this;
        }

        private UserBuilder withEmail(String email) {
            this.email = new EmailAddress(email);
            return this;
        }

        private UserBuilder withRole(UserRole role) {
            this.role = role;
            return this;
        }

        private User build() {
            return new User(username, password, email, role);
        }

        private User buildInitialized() {
            User user = new User(username, password, email, role);
            user.initialize();
            return user;
        }
    }
}
