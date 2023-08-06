package com.workoutly.application.user.api;


import com.workoutly.application.user.dto.command.AuthenticationCommand;
import com.workoutly.application.user.dto.response.TokenResponse;
import com.workoutly.application.user.port.input.UserApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserApplicationService userApplicationService;

    @PostMapping("/login")
    TokenResponse authenticate(@RequestBody AuthenticationCommand authenticationCommand) {
       TokenResponse response = userApplicationService.authenticate(authenticationCommand);
        return response;
    }
}
