package com.workoutly.application.user.builder;


import com.workoutly.application.user.dto.command.RegisterUserCommand;

public class RegisterUserCommandBuilder {
    String username;
    String emailAddress;
    String password;
    String confirmPassword;

    public static RegisterUserCommandBuilder aRegisterUserCommand() {
        return new RegisterUserCommandBuilder();
    }

    public RegisterUserCommandBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public RegisterUserCommandBuilder withEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
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
        return new RegisterUserCommand(username, emailAddress, password, confirmPassword);
    }
}
