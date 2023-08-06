package com.workoutly.application.user.api;

import com.workoutly.application.user.dto.command.ActivationUserCommand;
import com.workoutly.application.user.dto.command.RegisterUserCommand;
import com.workoutly.application.user.dto.response.MessageResponse;
import com.workoutly.application.user.dto.response.RegisterUserResponse;
import com.workoutly.application.user.mock.MockExceptionHandler;
import com.workoutly.application.user.port.input.UserApplicationService;
import com.workoutly.common.exception.ErrorResponse;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.workoutly.application.user.mock.MockExceptionHandler.createErrorResponse;
import static com.workoutly.application.user.utils.TestUtils.mapToString;
import static com.workoutly.application.user.builder.RegisterUserCommandBuilder.aRegisterUserCommand;
import static com.workoutly.application.user.utils.ResponseValidator.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
public class RegisterControllerTest {
    private final static String REGISTER_URL = "/api/register";
    private final static String ACTIVATION_URL = "/api/activate";

    private MockMvc mockMvc;

    @Mock
    private UserApplicationService userApplicationService;

    @InjectMocks
    private RegisterController controller;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new MockExceptionHandler())
                .build();
    }

    @Test
    public void testSuccessfulRegisterCommonUser() {
        // given
        var command = aRegisterUserCommand()
                .withUsername("example")
                .withEmailAddress("example@mail.to")
                .withPassword("Sup3rS3cureP@@s")
                .withConfirmPassword("Sup3rS3cureP@@s")
                .create();

        doReturn(successfullyRegistered(command)).when(userApplicationService).createCommonUser(command);

        // when
        performRegisterCommand(command);

        // then
        assertResponseStatusIs(isOk());
        assertResponseContentIs(successfullyRegistered(command));
    }

    @Test
    public void testRegisterFailure() {
        //given
        var command = aRegisterUserCommand()
                .withUsername("ex")
                .withEmailAddress("example@mail.to")
                .withPassword("Sup3rS3cureP@@s")
                .withConfirmPassword("Sup3rS3cureP@@s")
                .create();

        doThrow(new ValidationException())
                .when(userApplicationService)
                .createCommonUser(command);

        //when
        performRegisterCommand(command);

        //then
        assertResponseStatusIs(isBadRequest());
        assertResponseContentIs(errorResponse());
    }

    @Test
    public void testActivateUserAccount() {
        //given
        var command = new ActivationUserCommand("ABCDEFGHIJKLMNOPQRSTWUYZ");

        doReturn(successfullyActivated())
                .when(userApplicationService)
                .activateUserAccount(command);

        //when
        performActivationCommand(command);

        //then
        assertResponseStatusIs(isOk());
        assertResponseContentIs(successfullyActivated());
    }

    @Test
    public void testActivateUserFailure() {
        //given
        var command = new ActivationUserCommand("ABCDEFGHIJKLMNOPQRSTWUYZ");

        doThrow(new ValidationException())
                .when(userApplicationService)
                .activateUserAccount(command);

        //when
        performActivationCommand(command);

        //then
        assertResponseStatusIs(isBadRequest());
        assertResponseContentIs(errorResponse());
    }

    private ErrorResponse errorResponse() {
        return createErrorResponse();
    }

    private void performRegisterCommand(RegisterUserCommand request) {
        try {
            performRequest(request, REGISTER_URL);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void performActivationCommand(ActivationUserCommand command) {
        try {
            performRequest(command, ACTIVATION_URL);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void performRequest(Object request, String url) throws Exception{
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post(url)
                .content(mapToString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))

                .andReturn();

        setResult(result);
    }

    private RegisterUserResponse successfullyRegistered(RegisterUserCommand command) {
        return new RegisterUserResponse(
                "User created successfully.",
                command.getUsername()
        );
    }
    private MessageResponse successfullyActivated() {
        return new MessageResponse("User with bounded token activated");
    }

    private void assertResponseStatusIs(int status) {
        assertEquals(status, responseStatusIs());
    }

    private void assertResponseContentIs(Object response) {
        assertEquals(mapToString(response), responseContentIs());
    }
}