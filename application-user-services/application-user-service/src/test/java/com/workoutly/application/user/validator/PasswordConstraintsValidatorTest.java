package com.workoutly.application.user.validator;

import com.workoutly.application.user.mock.PasswordCommandMock;
import jakarta.validation.*;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PasswordConstraintsValidatorTest {

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
    public void testPasswordIsValid() {
        //given
        var password = new PasswordCommandMock("Super$ecu4e");

        //when
        var validationResponse = validator.validate(password);

        //then
        assertIsResponseEmpty(validationResponse);
    }

    @Test
    public void testPasswordIsTooShort() {
        //given
        var password = new PasswordCommandMock("Sup3%");

        //when
        var validationResponse = validator.validate(password);

        //then
        assertIsTooShortConstraint(validationResponse);
    }

    @Test
    public void testPasswordIsWithoutUpperCase() {
        //given
        var password = new PasswordCommandMock("super$ecu4e");

        //when
        var validationResponse = validator.validate(password);

        //then
        assertWithoutUpperCaseConstraint(validationResponse);
    }

    @Test
    public void testPasswordIsWithoutLowerCase() {
        //given
        var password = new PasswordCommandMock("SUPER$ECU4E");

        //when
        var validationResponse = validator.validate(password);

        //then
        assertWithoutLowerCaseConstraint(validationResponse);
    }

    @Test
    public void testPasswordIsWithoutDigit() {
        //given
        var password = new PasswordCommandMock("Super$ecure");

        //when
        var validationResponse = validator.validate(password);

        //then
        assertWithoutDigitCharacterConstraint(validationResponse);
    }

    @Test
    public void testPasswordIsWithoutSpecial() {
        //given
        var password = new PasswordCommandMock("Super2ecure");

        //when
        var validationResponse = validator.validate(password);

        //then
        assertWithoutSpecialCharacterConstraint(validationResponse);
    }

    private void assertIsResponseEmpty(Set<ConstraintViolation<PasswordCommandMock>> validationResponse) {
        assertTrue(validationResponse.isEmpty());
    }

    private void assertIsTooShortConstraint(Set<ConstraintViolation<PasswordCommandMock>> validationResponse) {
        assertIsOneViolation(validationResponse.size());
        assertConstraintMessage(validationResponse.iterator().next(),
                "Password must be 8 or more characters in length.");
    }

    private void assertWithoutUpperCaseConstraint(Set<ConstraintViolation<PasswordCommandMock>> validationResponse) {
        assertIsOneViolation(validationResponse.size());
        assertConstraintMessage(validationResponse.iterator().next(),
                "Password must contain 1 or more uppercase characters.");
    }

    private void assertWithoutLowerCaseConstraint(Set<ConstraintViolation<PasswordCommandMock>> validationResponse) {
        assertIsOneViolation(validationResponse.size());
        assertConstraintMessage(validationResponse.iterator().next(),
                "Password must contain 1 or more lowercase characters.");
    }

    private void assertWithoutDigitCharacterConstraint(Set<ConstraintViolation<PasswordCommandMock>> validationResponse) {
        assertIsOneViolation(validationResponse.size());
        assertConstraintMessage(validationResponse.iterator().next(),
                "Password must contain 1 or more digit characters.");
    }

    private void assertWithoutSpecialCharacterConstraint(Set<ConstraintViolation<PasswordCommandMock>> validationResponse) {
        assertIsOneViolation(validationResponse.size());
        assertConstraintMessage(validationResponse.iterator().next(),
                "Password must contain 1 or more special characters.");
    }

    private void assertConstraintMessage(ConstraintViolation<PasswordCommandMock> violation, String message) {
        assertTrue(violation.getMessage().contains(message));
    }

    private void assertIsOneViolation(int size) {
        assertEquals(1, size);
    }
}
