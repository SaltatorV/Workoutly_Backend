package com.workoutly.application.user.mock;

import com.workoutly.application.user.validator.ValidPassword;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PasswordCommandMock {

    @ValidPassword
    private String password;
}