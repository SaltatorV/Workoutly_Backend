package com.workoutly.application.user.api;

import com.workoutly.application.user.configuration.BeanConfiguration;
import com.workoutly.application.user.dto.command.RegisterUserCommand;
import com.workoutly.application.user.mock.MockConstraintViolationExceptionHandler;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import static com.workoutly.application.user.utils.TestUtils.asJsonString;
import static com.workoutly.application.user.builder.RegisterUserCommandBuilder.aRegisterUserCommand;
import static com.workoutly.application.user.utils.ResponseValidator.*;

@WebMvcTest(RegisterController.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RegisterController.class,
        BeanConfiguration.class,
        MockConstraintViolationExceptionHandler.class})

public class RegisterControllerTest {
    private final static String REGISTER_URL = "/auth/register";

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private Validator validator;

    @BeforeEach()
    public void setup() {
        Locale.setDefault(Locale.ENGLISH);
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testSuccessfulRegisterCommonUser() throws Exception {
        // given
        var request = aRegisterUserCommand()
                .withUsername("example")
                .withEmailAddress("example@mail.to")
                .withPassword("Sup3rS3cureP@@s")
                .withConfirmPassword("Sup3rS3cureP@@s")
                .create();

        // when
        performRegisterRequest(request);

        // then
        responseStatusIs(isOk());
        responseContentIs(successfullyCreated(request));
    }

    @Test
    public void testRegisterFailureWithInvalidUsername() throws Exception {
        //given
        var request = aRegisterUserCommand()
                .withUsername("ex")
                .withEmailAddress("example@mail.to")
                .withPassword("Sup3rS3cureP@@s")
                .withConfirmPassword("Sup3rS3cureP@@s")
                .create();

        //when
        performRegisterRequest(request);

        //then
        responseStatusIs(isBadRequest());
        responseContentIs(validationErrorOccurred(request));
    }

    private void performRegisterRequest(RegisterUserCommand request) throws Exception {
        performRequest(request, REGISTER_URL);
    }

    private void performRequest(Object request, String url) throws Exception{
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .post(url)
                .content(asJsonString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        setResult(result);
    }

    private List<String> successfullyCreated(RegisterUserCommand request) {
        String message = String.format("User: %s created successfully, check your e-mail address to activate account", request.getUsername());
        return List.of(message);
    }

    private List<String> validationErrorOccurred(RegisterUserCommand command) {
        List<String> contentValues = new ArrayList<>();
        String message = "Validation error occurred";
        contentValues.add(message);
        Set<ConstraintViolation<RegisterUserCommand>> result = validator.validate(command);
        for (ConstraintViolation violation: result) {
            contentValues.add(violation.getMessage());
        }
        return contentValues;
    }

}
