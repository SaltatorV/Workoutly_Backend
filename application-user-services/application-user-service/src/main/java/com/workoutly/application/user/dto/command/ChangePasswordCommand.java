package com.workoutly.application.user.dto.command;

import com.workoutly.application.user.validator.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordCommand {

    @NotEmpty
    private String oldPassword;

    @NotEmpty
    @ValidPassword
    private String newPassword;
}
