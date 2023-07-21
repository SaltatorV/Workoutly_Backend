package com.workoutly.application.user.dto.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterUserCommand {
    private String username;
    private String email;
    private String password;
    private String confirmPassword;
}
