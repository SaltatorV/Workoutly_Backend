package com.workoutly.application.user.api;

import com.workoutly.application.user.dto.command.ChangeEmailCommand;
import com.workoutly.application.user.dto.command.ChangePasswordCommand;
import com.workoutly.application.user.dto.response.MessageResponse;
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
import static com.workoutly.application.user.utils.ResponseValidator.*;
import static com.workoutly.application.user.utils.ResponseValidator.responseContentIs;
import static com.workoutly.application.user.utils.TestUtils.mapToString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {
    private final static String CHANGE_EMAIL_URL = "/api/change/email";
    private final static String CHANGE_PASSWORD_URL = "/api/change/password";

    private MockMvc mockMvc;

    @Mock
    private UserApplicationService service;

    @InjectMocks
    private AccountController controller;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new MockExceptionHandler())
                .build();
    }

    @Test
    public void testChangeEmail() {
        //given
        var command = new ChangeEmailCommand("example@example.to", "password");
        var response = new MessageResponse("Email has been changed");

        doReturn(response)
                .when(service)
                .changeEmail(command);

        //when
        performChangeEmailCommand(command);

        //then
        assertResponseStatusIs(isOk());
        assertResponseContentIs(response);
    }

    @Test
    public void testChangeEmailFailure() {
        //given
        var command = new ChangeEmailCommand("example@example.to", "password");

        doThrow(new ValidationException())
                .when(service)
                .changeEmail(command);

        //when
        performChangeEmailCommand(command);

        //then
        assertResponseStatusIs(isBadRequest());
        assertResponseContentIs(errorResponse());
    }

    @Test
    public void testChangePassword() {
        //given
        var command = new ChangePasswordCommand("password", "new-password");
        var response = new MessageResponse("Password has been changed");

        doReturn(response)
                .when(service)
                .changePassword(command);

        //when
        performChangePasswordCommand(command);

        //then
        assertResponseStatusIs(isOk());
        assertResponseContentIs(response);
    }

    @Test
    public void testChangePasswordFailure() {
        //given
        var command = new ChangePasswordCommand("password", "new-password");

        doThrow(new ValidationException())
                .when(service)
                .changePassword(command);

        //when
        performChangePasswordCommand(command);

        //then
        assertResponseStatusIs(isBadRequest());
        assertResponseContentIs(errorResponse());
    }

    private void performChangeEmailCommand(ChangeEmailCommand request) {
        try {
            performRequest(request, CHANGE_EMAIL_URL);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void performChangePasswordCommand(ChangePasswordCommand request) {
        try {
            performRequest(request, CHANGE_PASSWORD_URL);
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

    private void assertResponseStatusIs(int status) {
        assertEquals(status, responseStatusIs());
    }

    private void assertResponseContentIs(Object response) {
        assertEquals(mapToString(response), responseContentIs());
    }

    private ErrorResponse errorResponse() {
        return createErrorResponse();
    }
}
