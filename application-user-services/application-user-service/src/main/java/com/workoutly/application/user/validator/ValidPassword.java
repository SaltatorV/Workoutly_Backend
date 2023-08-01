package com.workoutly.application.user.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ValidPasswordValidator.class)
public @interface ValidPassword {

    String message() default "{com.workoutly.application.user.validator.ValidPassword.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}