package com.workoutly.application.user.dto.command;

import com.workoutly.application.user.validator.ConfirmPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@ConfirmPassword
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserCommand {
    @Size(min = 3, max = 20)
    private String username;

    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    private String confirmPassword;
}
