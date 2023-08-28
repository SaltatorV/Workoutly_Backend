package com.workoutly.application.user.mapper;

import com.workoutly.application.user.User;
import com.workoutly.application.user.VO.UserId;
import com.workoutly.application.user.VO.UserRole;
import com.workoutly.application.user.VO.UserSnapshot;
import com.workoutly.application.user.dto.command.RegisterUserCommand;
import com.workoutly.application.user.dto.response.MessageResponse;
import com.workoutly.application.user.dto.response.RegisterUserResponse;
import com.workoutly.application.user.event.UserActivatedEvent;
import com.workoutly.application.user.event.UserCreatedEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static com.workoutly.application.user.builder.RegisterUserCommandBuilder.aRegisterUserCommand;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class UserDataMapperTest {
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserDataMapper userDataMapper;

    @Test
    public void testRegisterUserCommandToCommonUser() {
        //given
        var command = aRegisterUserCommand()
                .withUsername("Test")
                .withPassword("Password")
                .withConfirmPassword("Password")
                .withEmailAddress("Example@example.pl")
                .create();

        doReturn(command.getPassword())
                .when(passwordEncoder)
                .encode(command.getPassword());

        //when
        var user = userDataMapper.registerUserCommandToCommonUser(command);

        //then
        assertIsCommonUserValid(user, command);

    }

    @Test
    public void testUserCreatedEventToRegisterUserResponse() {
        //given
        var command = aRegisterUserCommand()
                .withUsername("Test")
                .withPassword("Password")
                .withConfirmPassword("Password")
                .withEmailAddress("Example@example.pl")
                .create();

        var event = new UserCreatedEvent(createCommonUserSnapshot(command));

        //when
        var response = userDataMapper.userCreatedEventToRegisterUserResponse(event);

        //then
        assertIsResponseValid(response, event);
    }

    @Test
    public void testTokenToTokenResponse() {
        //given
        var token = createRandomToken();

        //when
        var response = userDataMapper.mapTokenToTokenResponse(token);

        //then
        assertEquals(response.getToken(), token);
    }

    @Test
    public void testMapUserActivatedEventToMessageResponse() {
        //given
        var snapshot = createUserSnapshot();
        var event = new UserActivatedEvent(snapshot);

        //when
        var response = userDataMapper.mapUserActivatedEventToMessageResponse(event);

        //then
        assertIsResponseValid(response, event);
    }

    @Test
    public void testMapUserUpdatedEmailEventToMessageResponse() {
        //given

        //when
        var response = userDataMapper.mapUserUpdatedEmailEventToMessageResponse();

        //then
        assertResponseIsChangeEmail(response);
    }

    @Test
    public void testMapUserUpdatedPasswordEventToMessageResponse() {
        //given

        //when
        var response = userDataMapper.mapUserUpdatedPasswordEventToMessageResponse();

        //then
        assertResponseIsChangePassword(response);
    }

    private void assertIsCommonUserValid(User user, RegisterUserCommand command) {
        UserSnapshot snapshot = user.createSnapshot();
        UserSnapshot commandSnapshot = createCommonUserSnapshot(command);
        assertEquals(commandSnapshot, snapshot);
    }

    private UserSnapshot createCommonUserSnapshot(RegisterUserCommand command) {
        return new User(command.getUsername(), command.getPassword(), command.getEmail(), UserRole.COMMON_USER)
                .createSnapshot();
    }

    private void assertIsResponseValid(RegisterUserResponse response, UserCreatedEvent event) {
        assertEquals(event.getSnapshot().getUsername(), response.getUsername());
    }

    private void assertIsResponseValid(MessageResponse response, UserActivatedEvent event) {
        assertEquals(String.format("User: %s has been activated", event.getSnapshot().getUsername()), response.getMessage());
    }

    private void assertResponseIsChangeEmail(MessageResponse response) {
        assertEquals("The email address has been changed.", response.getMessage());
    }

    private void assertResponseIsChangePassword(MessageResponse response) {
        assertEquals("The password has been changed.", response.getMessage());
    }

    private String createRandomToken() {
        return UUID.randomUUID().toString();
    }

    private UserSnapshot createUserSnapshot() {
        return new UserSnapshot(
                new UserId(UUID.randomUUID()),
                "test",
                "test@test.com",
                "password",
                UserRole.COMMON_USER,
                true,
                null
        );
    }
}
