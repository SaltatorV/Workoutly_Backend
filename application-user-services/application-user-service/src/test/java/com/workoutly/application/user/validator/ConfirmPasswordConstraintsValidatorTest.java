package com.workoutly.application.user.validator;

import com.workoutly.application.user.dto.command.RegisterUserCommand;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.workoutly.application.user.builder.RegisterUserCommandBuilder.aRegisterUserCommand;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConfirmPasswordConstraintsValidatorTest {

    private static Validator validator;

    @BeforeEach
    public void createValidator() {
        validator = Validation.byDefaultProvider()
                .configure()
                .messageInterpolator(new ParameterMessageInterpolator())
                .buildValidatorFactory()
                .getValidator();
    }

    @Test
    public void testPasswordsAreEqual() {

        //given
        var command = aRegisterUserCommand()
                .withUsername("test")
                .withEmailAddress("test@test.to")
                .withPassword("password")
                .withConfirmPassword("password")
                .create();

        //when
        var validationResponse = validator.validate(command);

        //then
        assertIsResponseEmpty(validationResponse);
    }

    @Test
    public void testPasswordsAreNotEqual() {

        //given
        var command = aRegisterUserCommand()
                .withUsername("test")
                .withEmailAddress("test@test.to")
                .withPassword("password")
                .withConfirmPassword("test")
                .create();

        //when
        var validationResponse = validator.validate(command);

        //then
        assertPasswordsNotEqual(validationResponse);
    }

    @Test
    public void testPasswordsAreNotEqualWithEmptyPassword() {

        //given
        var command = aRegisterUserCommand()
                .withUsername("test")
                .withEmailAddress("test@test.to")
                .withPassword("")
                .withConfirmPassword("password")
                .create();

        //when
        var validationResponse = validator.validate(command);

        //then
        assertPasswordsNotEqual(validationResponse);
    }

    @Test
    public void testPasswordsAreNotEqualWithEmptyConfirmPassword() {

        //given
        var command = aRegisterUserCommand()
                .withUsername("test")
                .withEmailAddress("test@test.to")
                .withPassword("password")
                .withConfirmPassword("")
                .create();

        //when
        var validationResponse = validator.validate(command);

        //then
        assertPasswordsNotEqual(validationResponse);
    }

    @Test
    public void testPasswordsAreNotEqualWithUpperCase() {

        //given
        var command = aRegisterUserCommand()
                .withUsername("test")
                .withEmailAddress("test@test.to")
                .withPassword("PASSWORD")
                .withConfirmPassword("password")
                .create();

        //when
        var validationResponse = validator.validate(command);

        //then
        assertPasswordsNotEqual(validationResponse);
    }


    private void assertIsResponseEmpty(Set<ConstraintViolation<RegisterUserCommand>> validationResponse) {
        assertTrue(validationResponse.isEmpty());
    }


    private void assertPasswordsNotEqual(Set<ConstraintViolation<RegisterUserCommand>> validationResponse) {
        assertEquals("Passwords don't match.",validationResponse.iterator().next().getMessage());
    }
}
