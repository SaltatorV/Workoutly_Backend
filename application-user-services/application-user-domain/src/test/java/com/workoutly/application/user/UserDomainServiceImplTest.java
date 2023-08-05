package com.workoutly.application.user;

import com.workoutly.application.user.VO.UserRole;
import com.workoutly.application.user.VO.UserSnapshot;
import com.workoutly.application.user.event.UserActivatedEvent;
import com.workoutly.application.user.event.UserCreatedEvent;
import org.junit.jupiter.api.Test;

import static com.workoutly.application.user.builder.UserBuilder.anUser;
import static org.junit.jupiter.api.Assertions.*;

public class UserDomainServiceImplTest {

    UserDomainService service = new UserDomainServiceImpl();

    @Test
    public void testInitializeUser() {
        //given
        User user = anUser()
                .withUsername("Test")
                .withPassword("Pa$$word")
                .withEmail("example@example.com")
                .withRole(UserRole.COMMON_USER)
                .build();

        //when
        UserCreatedEvent event = service.initializeUser(user);

        //then
        assertUserIsInitialized(event);
    }

    @Test
    public void testActivateUser() {
        //given
        User user = anUser()
                .withUsername("Test")
                .withPassword("Pa$$word")
                .withEmail("example@example.com")
                .withRole(UserRole.COMMON_USER)
                .withVerificationToken(VerificationToken.generateToken())
                .build();

        //when
        UserActivatedEvent event = service.activateUser(user);

        //then
        assertUserIsActivated(event);
    }

    private void assertUserIsInitialized(UserCreatedEvent event) {
        UserSnapshot snapshot = event.getSnapshot();
        assertNotNull(snapshot.getUserId());
        assertFalse(snapshot.isEnabled());
    }

    private void assertUserIsActivated(UserActivatedEvent event) {
        UserSnapshot snapshot = event.getSnapshot();
        assertTrue(snapshot.isEnabled());
    }

}
