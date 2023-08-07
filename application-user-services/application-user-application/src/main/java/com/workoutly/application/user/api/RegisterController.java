package com.workoutly.application.user.api;

import com.workoutly.application.user.dto.command.RegisterUserCommand;
import com.workoutly.application.user.dto.command.ActivationUserCommand;
import com.workoutly.application.user.dto.response.MessageResponse;
import com.workoutly.application.user.dto.response.RegisterUserResponse;
import com.workoutly.application.user.port.input.UserApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class RegisterController {
    private final UserApplicationService service;

    @PostMapping("/register")
    RegisterUserResponse createCommonAccount(@RequestBody RegisterUserCommand registerUserCommand) {
        return service.createCommonUser(registerUserCommand);
    }

    @PostMapping("/activate")
    MessageResponse activateAccount(@RequestBody ActivationUserCommand activationUserCommand) {
        return service.activateUserAccount(activationUserCommand);
    }
}
