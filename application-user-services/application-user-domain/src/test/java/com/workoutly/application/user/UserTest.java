package com.workoutly.application.user;

import com.workoutly.application.user.VO.*;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.Instant;
import java.util.UUID;

import static com.workoutly.application.user.VO.UserSnapshot.Builder.anUserSnapshot;
import static com.workoutly.application.user.builder.UserBuilder.anUser;
import static com.workoutly.application.user.utils.TestUtils.mapToString;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void testInitializeUser() {
        //given
        var user = anUser()
                .withUsername("Test")
                .withPassword("Pa$$word")
                .withEmail("example@example.com")
                .withRole(UserRole.COMMON_USER)
                .build();

        //when
        user.initialize();

        //then
        assertUserIsInitialized(user);
    }

    @Test
    public void testEnableUser() {
        //given
        var user = anUser()
                .withUsername("Test")
                .withPassword("Pa$$word")
                .withEmail("example@example.com")
                .withRole(UserRole.COMMON_USER)
                .buildInitialized();

        //when
        user.enable();

        //then
        assertUserIsEnabled(user);
    }

    @Test
    public void testChangePassword() {
        //given
        var user = anUser()
                .withUsername("Test")
                .withPassword("Pa$$word")
                .withEmail("example@example.com")
                .withRole(UserRole.COMMON_USER)
                .withVerificationToken(VerificationToken.restore(createTokenSnapshot()))
                .build();

        //when
        user.changePassword("test");

        //then
        assertEquals("test", user.createSnapshot().getPassword());
    }

    @Test
    public void testChangeEmail() {
        //given
        var user = anUser()
                .withUsername("Test")
                .withPassword("Pa$$word")
                .withEmail("example@example.com")
                .withRole(UserRole.COMMON_USER)
                .withVerificationToken(VerificationToken.restore(createTokenSnapshot()))
                .build();

        //when
        user.changeEmail("test@test.com");

        //then
        assertEquals("test@test.com", user.createSnapshot().getEmail());
    }

    @Test
    public void testCreateUserSnapshot() {
        //given
        var mockSnapshot = anUserSnapshot()
                .withUsername("Test")
                .withPassword("Pa$$word")
                .withEmail("example@example.com")
                .withRole(UserRole.COMMON_USER)
                .withIsEnabled(false)
                .withToken(createTokenSnapshot())
                .build();

        var user = anUser()
                .withUsername("Test")
                .withPassword("Pa$$word")
                .withEmail("example@example.com")
                .withRole(UserRole.COMMON_USER)
                .withVerificationToken(VerificationToken.restore(mockSnapshot.getToken()))
                .build();

        //when
        var snapshot = user.createSnapshot();

        //then
        assertSnapshotsAreEqual(snapshot, mockSnapshot);
    }

    @Test
    public void testCreateUserSnapshotWithInitializedUser() {
        //given
        var user = anUser()
                .withUsername("Test")
                .withPassword("Pa$$word")
                .withEmail("example@example.com")
                .withRole(UserRole.COMMON_USER)
                .buildInitialized();

        var mockSnapshot = anUserSnapshot()
                .withUserId(user.getId())
                .withUsername("Test")
                .withPassword("Pa$$word")
                .withEmail("example@example.com")
                .withRole(UserRole.COMMON_USER)
                .withIsEnabled(false)
                .withToken(user.createSnapshot().getToken())
                .build();

        //when
        var snapshot = user.createSnapshot();

        //then
        assertSnapshotsAreEqual(snapshot, mockSnapshot);
    }

    @Test
    public void testCreateUserSnapshotWithEnabledUser() {
        //given
        var mockSnapshot = anUserSnapshot()
                .withUserId(new UserId(UUID.randomUUID()))
                .withUsername("Test")
                .withPassword("Pa$$word")
                .withEmail("example@example.com")
                .withRole(UserRole.COMMON_USER)
                .withIsEnabled(true)
                .withToken(createTokenSnapshot())
                .build();

        var user = User.restore(mockSnapshot);

        //when
        var snapshot = user.createSnapshot();

        //then
        assertSnapshotsAreEqual(snapshot, mockSnapshot);
    }

    @Test
    public void testRestoreUser() {
        //given
        var snapshot = anUserSnapshot()
                .withUsername("Test")
                .withPassword("Pa$$word")
                .withEmail("example@example.com")
                .withRole(UserRole.COMMON_USER)
                .withToken(createTokenSnapshot())
                .build();

        var mockUser = anUser()
                .withUsername("Test")
                .withPassword("Pa$$word")
                .withEmail("example@example.com")
                .withRole(UserRole.COMMON_USER)
                .withVerificationToken(VerificationToken.restore(snapshot.getToken()))
                .build();


        //when
        var user = User.restore(snapshot);

        //then
        assertUsersAreEqual(user, mockUser);
    }

    @Test
    public void testRestoreInitializedUser() {
        //given
        var userId = new UserId(UUID.randomUUID());

        var snapshot = anUserSnapshot()
                .withUserId(userId)
                .withUsername("Test")
                .withPassword("Pa$$word")
                .withEmail("example@example.com")
                .withRole(UserRole.COMMON_USER)
                .withToken(createTokenSnapshot())
                .build();

        var mockUser = anUser()
                .withUserId(userId)
                .withUsername("Test")
                .withPassword("Pa$$word")
                .withEmail("example@example.com")
                .withRole(UserRole.COMMON_USER)
                .withVerificationToken(VerificationToken.restore(createTokenSnapshot()))
                .build();

        //when
        User user = User.restore(snapshot);

        //then
        assertUsersAreEqual(user, mockUser);
    }

    @Test
    public void testRestoreEnabledUser() {
        //given
        var userId = new UserId(UUID.randomUUID());
        var tokenSnapshot = createTokenSnapshot();
        var snapshot = anUserSnapshot()
                .withUserId(userId)
                .withUsername("Test")
                .withPassword("Pa$$word")
                .withEmail("example@example.com")
                .withRole(UserRole.COMMON_USER)
                .withIsEnabled(true)
                .withToken(tokenSnapshot)
                .build();

        var mockUser = anUser()
                .withUserId(userId)
                .withUsername("Test")
                .withPassword("Pa$$word")
                .withEmail("example@example.com")
                .withRole(UserRole.COMMON_USER)
                .withVerificationToken(VerificationToken.restore(tokenSnapshot))
                .buildEnabled();

        //when
        var user = User.restore(snapshot);

        //then
        assertUsersAreEqual(user, mockUser);
    }


    private VerificationTokenSnapshot createTokenSnapshot() {
        return new VerificationTokenSnapshot(new TokenId(UUID.randomUUID()),UUID.randomUUID().toString(), Date.from(Instant.now() ));
    }

    private void assertUserIsInitialized(User user) {
        assertNotNull(user.getId());
        assertNotNull(user.createSnapshot().getToken());
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

}
