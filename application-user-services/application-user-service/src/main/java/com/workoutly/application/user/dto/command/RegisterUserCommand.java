package com.workoutly.application.user.dto.command;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserCommand {
    @NotNull
    @Size(min = 3, max = 20)
    private String username;

    @Email
    @NotNull
    private String email;

    @NotEmpty
    private String password;

    @NotEmpty
    private String confirmPassword;
}
