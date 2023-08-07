package com.workoutly.application.user.api;

import com.workoutly.application.user.dto.command.ChangeEmailCommand;
import com.workoutly.application.user.dto.command.ChangePasswordCommand;
import com.workoutly.application.user.dto.response.MessageResponse;
import com.workoutly.application.user.port.input.UserApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class AccountController {
    private final UserApplicationService service;

    @PostMapping("/change/email")
    MessageResponse changeEmail(@RequestBody ChangeEmailCommand command) {
        return service.changeEmail(command);
    }

    @PostMapping("/change/password")
    MessageResponse changePassword(@RequestBody ChangePasswordCommand command) {
        return service.changePassword(command);
    }
}
