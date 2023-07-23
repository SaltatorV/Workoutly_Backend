package com.workoutly.application.user.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RegisterUserResponse {

    private final String message;
    private final String username;
}
