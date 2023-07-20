package com.workoutly.application.user.dto.command;

public class RegisterUserCommand {
    private String username;
    private String email;
    private String password;
    private String confirmPassword;

    public RegisterUserCommand(String username, String email, String password, String confirmPassword) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }
}
