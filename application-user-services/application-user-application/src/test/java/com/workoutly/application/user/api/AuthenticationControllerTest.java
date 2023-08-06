package com.workoutly.application.user.api;


import com.workoutly.application.user.dto.command.ActivationUserCommand;
import com.workoutly.application.user.dto.command.AuthenticationCommand;
import com.workoutly.application.user.dto.response.TokenResponse;
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

import java.util.UUID;

import static com.workoutly.application.user.mock.MockExceptionHandler.createErrorResponse;
import static com.workoutly.application.user.utils.ResponseValidator.*;
import static com.workoutly.application.user.utils.ResponseValidator.responseContentIs;
import static com.workoutly.application.user.utils.TestUtils.mapToString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
public class AuthenticationControllerTest {
    private final static String AUTHENTICATE_URL = "/api/login";

    private MockMvc mockMvc;

    @Mock
    private UserApplicationService userApplicationService;

    @InjectMocks
    private AuthenticationController controller;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new MockExceptionHandler())
                .build();
    }

    @Test
    public void testSuccessfulAuthentication() {
        // given
        var command = new AuthenticationCommand("test", "Super$ecure5");
        var response = createTokenResponse();

        doReturn(response)
                .when(userApplicationService)
                .authenticate(command);

        // when
        performAuthenticationCommand(command);

        // then
        assertResponseStatusIs(isOk());
        assertResponseContentIs(response);
    }

    @Test
    public void testAuthenticationFailure() {
        //given
        var command = new AuthenticationCommand("test", "Super$ecure5");

        doThrow(new ValidationException())
                .when(userApplicationService)
                .authenticate(command);

        //when
        performAuthenticationCommand(command);

        //then
        assertResponseStatusIs(isBadRequest());
        assertResponseContentIs(errorResponse());
    }

    private TokenResponse createTokenResponse() {
        return new TokenResponse(UUID.randomUUID().toString());
    }

    private void performAuthenticationCommand(AuthenticationCommand request) {
        try {
            performRequest(request, AUTHENTICATE_URL);
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
