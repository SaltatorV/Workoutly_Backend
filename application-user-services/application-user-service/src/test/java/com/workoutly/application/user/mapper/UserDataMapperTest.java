package com.workoutly.application.user.mapper;

import com.workoutly.application.user.User;
import com.workoutly.application.user.VO.UserRole;
import com.workoutly.application.user.VO.UserSnapshot;
import com.workoutly.application.user.dto.command.RegisterUserCommand;
import com.workoutly.application.user.dto.response.RegisterUserResponse;
import com.workoutly.application.user.event.UserCreatedEvent;
import org.junit.jupiter.api.Test;

import static com.workoutly.application.user.builder.RegisterUserCommandBuilder.aRegisterUserCommand;
import static com.workoutly.application.user.utils.TestUtils.mapToString;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserDataMapperTest {

    private UserDataMapper userDataMapper = new UserDataMapper();

    @Test
    public void testRegisterUserCommandToCommonUser() {
        //given
        var command = aRegisterUserCommand()
                .withUsername("Test")
                .withPassword("Password")
                .withConfirmPassword("Password")
                .withEmailAddress("Example@example.pl")
                .create();

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

    private void assertIsCommonUserValid(User user, RegisterUserCommand command) {
        UserSnapshot snapshot = user.createSnapshot();
        UserSnapshot commandSnapshot = createCommonUserSnapshot(command);
        assertEquals(mapToString(commandSnapshot), mapToString(snapshot));
    }

    private UserSnapshot createCommonUserSnapshot(RegisterUserCommand command) {
        return new User(command.getUsername(), command.getPassword(), command.getEmail(), UserRole.COMMON)
                .createSnapshot();
    }

    private void assertIsResponseValid(RegisterUserResponse response, UserCreatedEvent event) {
        assertEquals(event.getSnapshot().getUsername(), response.getUsername());
    }
}
