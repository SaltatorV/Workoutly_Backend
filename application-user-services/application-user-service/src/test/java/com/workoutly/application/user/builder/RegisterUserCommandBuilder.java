package com.workoutly.application.user.builder;

import com.workoutly.application.user.dto.command.RegisterUserCommand;

public class RegisterUserCommandBuilder {
    private String username;
    private String email;
    private String password;
    private String confirmPassword;


    public RegisterUserCommandBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public RegisterUserCommandBuilder withEmailAddress(String email) {
        this.email = email;
        return this;
    }

    public RegisterUserCommandBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public RegisterUserCommandBuilder withConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }

    public RegisterUserCommand create() {
        return new RegisterUserCommand(username, email, password, confirmPassword);
    }
}