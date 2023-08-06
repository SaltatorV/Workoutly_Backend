package com.workoutly.application.user.dto.command;

import com.workoutly.application.user.validator.ValidPassword;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationCommand {
    @NotNull
    private String username;
    @NotNull
    @ValidPassword
    private String password;
}
