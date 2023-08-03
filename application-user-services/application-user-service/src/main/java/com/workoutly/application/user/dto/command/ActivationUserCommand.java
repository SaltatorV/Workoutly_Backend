package com.workoutly.application.user.dto.command;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivationUserCommand {
    @NotEmpty
    private String token;
}
