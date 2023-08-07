package com.workoutly.application.user.dto.command;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeEmailCommand {

    @Email
    @NotEmpty
    private String emailAddress;

    @NotEmpty
    private String password;
}
