package com.workoutly.application.user.api;

import com.workoutly.application.user.UserApplicationServiceImpl;
import com.workoutly.application.user.dto.command.RegisterUserCommand;
import com.workoutly.application.user.dto.response.RegisterUserResponse;
import com.workoutly.application.user.mock.MockExceptionHandler;
import com.workoutly.common.exception.ErrorResponse;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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

public class RegisterControllerTest {
    private final static String REGISTER_URL = "/auth/register";

    private MockMvc mockMvc;
    @Mock
    private UserApplicationServiceImpl userApplicationService;

    @InjectMocks
    private RegisterController controller;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new MockExceptionHandler())
                .build();
    }

    @Test
    public void testSuccessfulRegisterCommonUser() throws Exception {
        // given
        var command = aRegisterUserCommand()
                .withUsername("example")
                .withEmailAddress("example@mail.to")
                .withPassword("Sup3rS3cureP@@s")
                .withConfirmPassword("Sup3rS3cureP@@s")
                .create();

        doReturn(successfullyRegistered(command)).when(userApplicationService).createCommonUser(command);

        // when
        performRegisterRequest(command);

        // then
        assertResponseStatusIs(isOk());
        assertResponseContentIs(successfullyRegistered(command));
    }

    @Test
    public void testRegisterFailure() throws Exception {
        //given
        var command = aRegisterUserCommand()
                .withUsername("ex")
                .withEmailAddress("example@mail.to")
                .withPassword("Sup3rS3cureP@@s")
                .withConfirmPassword("Sup3rS3cureP@@s")
                .create();

        doThrow(new ValidationException()).when(userApplicationService).createCommonUser(command);

        //when
        performRegisterRequest(command);

        //then
        assertResponseStatusIs(isBadRequest());
        assertResponseContentIs(registerFailure());
    }

    private ErrorResponse registerFailure() {
        return createErrorResponse();
    }

    private void performRegisterRequest(RegisterUserCommand request) throws Exception {
        performRequest(request, REGISTER_URL);
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


    private void assertResponseStatusIs(int status) {
        assertEquals(status, responseStatusIs());
    }

    private void assertResponseContentIs(Object response) {
        assertEquals(mapToString(response), responseContentIs());
    }
}

