package com.workoutly.application.user.dto.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public class RegisterUserResponse {

    private final String message;
    private final String username;
}
