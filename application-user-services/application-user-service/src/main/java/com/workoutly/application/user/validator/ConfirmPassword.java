package com.workoutly.application.user.validator;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ConfirmPasswordConstraintsValidator.class)
public @interface ConfirmPassword {

    String message() default "{com.workoutly.application.user.validator.ConfirmPassword.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}