package com.workoutly.application.user.mapper;

import com.workoutly.application.user.User;
import com.workoutly.application.user.VO.UserRole;
import com.workoutly.application.user.VO.UserSnapshot;
import com.workoutly.application.user.dto.command.RegisterUserCommand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.workoutly.application.user.builder.RegisterUserCommandBuilder.aRegisterUserCommand;
import static com.workoutly.application.user.utils.TestUtils.mapToString;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {UserDataMapper.class})
public class UserDataMapperTest {

    @Autowired
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

        //when
        var user = userDataMapper.registerUserCommandToCommonUser(command);

        //then
        assertIsCommonUserValid(user, command);

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
}
