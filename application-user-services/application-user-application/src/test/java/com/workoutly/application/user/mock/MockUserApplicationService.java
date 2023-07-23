package com.workoutly.application.user.mock;

import com.workoutly.application.user.dto.command.RegisterUserCommand;
import com.workoutly.application.user.dto.response.RegisterUserResponse;
import com.workoutly.application.user.port.input.UserApplicationService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;


@Service
@Validated
public class MockUserApplicationService implements UserApplicationService {

  @Override
  public RegisterUserResponse createCommonUser(RegisterUserCommand registerUserCommand) {
    return createSuccessfulRegisterUserResponse(registerUserCommand);
  }

  private RegisterUserResponse createSuccessfulRegisterUserResponse(RegisterUserCommand registerUserCommand) {
    return new RegisterUserResponse(
        String.format(
            "User: %s created successfully, check your e-mail address to activate account", registerUserCommand.getUsername()),
            registerUserCommand.getUsername());
  }
}
