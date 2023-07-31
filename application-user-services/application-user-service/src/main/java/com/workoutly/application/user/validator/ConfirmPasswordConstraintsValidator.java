package com.workoutly.application.user.validator;

import com.workoutly.application.user.dto.command.RegisterUserCommand;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.NoArgsConstructor;


@NoArgsConstructor
public class ConfirmPasswordConstraintsValidator implements ConstraintValidator<ConfirmPassword, RegisterUserCommand> {

    @Override
    public boolean isValid(RegisterUserCommand command, ConstraintValidatorContext context) {
        return command.getPassword().equals(command.getConfirmPassword());
    }


}
