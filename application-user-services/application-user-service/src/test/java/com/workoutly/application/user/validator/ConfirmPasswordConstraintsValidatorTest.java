package com.workoutly.application.user.validator;

import org.junit.jupiter.api.Test;

import static com.workoutly.application.user.builder.RegisterUserCommandBuilder.aRegisterUserCommand;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConfirmPasswordConstraintsValidatorTest {

    private ConfirmPasswordConstraintsValidator validator = new ConfirmPasswordConstraintsValidator();

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
        var passwordsAreEqual = validator.isValid(command, null);

        //then
        assertTrue(passwordsAreEqual);
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
        var passwordsAreNotEqual = validator.isValid(command, null);

        //then
        assertFalse(passwordsAreNotEqual);
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
        var passwordsAreNotEqual = validator.isValid(command, null);

        //then
        assertFalse(passwordsAreNotEqual);
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
        var passwordsAreNotEqual = validator.isValid(command, null);

        //then
        assertFalse(passwordsAreNotEqual);
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
        var passwordsAreNotEqual = validator.isValid(command, null);

        //then
        assertFalse(passwordsAreNotEqual);
    }
}
