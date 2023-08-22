package com.workoutly.application.user;

import com.workoutly.application.user.VO.UserSnapshot;
import com.workoutly.application.user.auth.AuthenticationProvider;
import com.workoutly.application.user.auth.TokenService;
import com.workoutly.application.user.dto.command.*;
import com.workoutly.application.user.event.UserActivatedEvent;
import com.workoutly.application.user.event.UserUpdatedEvent;
import com.workoutly.application.user.event.UserCreatedEvent;
import com.workoutly.application.user.exception.*;
import com.workoutly.application.user.mapper.UserDataMapper;
import com.workoutly.application.user.port.output.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Component
@RequiredArgsConstructor
class UserCommandHandler {
    private final UserDataMapper userDataMapper;
    private final UserDomainService userDomainService;
    private final UserRepository userRepository;
    private final AuthenticationProvider authenticationProvider;
    private final TokenService tokenService;

    @Transactional
    public UserCreatedEvent createCommonUser(RegisterUserCommand registerUserCommand) {
        User user = userDataMapper.registerUserCommandToCommonUser(registerUserCommand);

        checkUserIsUnique(user.createSnapshot());

        UserCreatedEvent event = userDomainService.initializeUser(user);
        UserSnapshot savedSnapshot = userRepository.save(event.getSnapshot());

        checkUserIsSaved(savedSnapshot);

        return event;
    }

    @Transactional
    public UserActivatedEvent activateUser(ActivationUserCommand command) {
        Optional<UserSnapshot> snapshot = userRepository.findByVerificationToken(command.getToken());

        checkIsUserFound(snapshot);

        VerificationToken token = VerificationToken.restore(snapshot.get().getToken());

        verifyActivationToken(token);

        UserActivatedEvent event = userDomainService.activateUser(User.restore(snapshot.get()));
        userRepository.save(event.getSnapshot());

        return event;
    }

    @Transactional(readOnly = true)
    public String authenticate(AuthenticationCommand authenticationCommand) {

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                                    authenticationCommand.getUsername(),
                                                    authenticationCommand.getPassword()
                                                    );

        UserDetails userDetails = authenticationProvider.createUserDetails(authToken);
        return tokenService.generateNewToken(userDetails);
    }

    @Transactional
    public UserUpdatedEvent changeEmail(ChangeEmailCommand command) {
        UserSnapshot userSnapshot = userRepository.findByUsername(authenticationProvider.getAuthenticatedUsername());
        checkPasswordsMatch(command.getPassword(), userSnapshot);

        UserUpdatedEvent event = userDomainService.changeEmail(User.restore(userSnapshot), command.getEmailAddress());

        userRepository.save(event.getSnapshot());

        return event;
    }

    @Transactional
    public UserUpdatedEvent changePassword(ChangePasswordCommand command) {
        UserSnapshot userSnapshot = userRepository.findByUsername(authenticationProvider.getAuthenticatedUsername());
        checkPasswordsMatch(command.getPassword(), userSnapshot);

        UserUpdatedEvent event = userDomainService.changePassword(User.restore(userSnapshot), authenticationProvider.encodePassword(command.getNewPassword()));

        userRepository.save(event.getSnapshot());

        return event;
    }

    private void checkUserIsUnique(UserSnapshot snapshot) {
        if(userRepository.checkUserExists(snapshot)) {
            throw new UserNotUniqueException();
        }
    }

    private void checkUserIsSaved(UserSnapshot snapshot) {
        if (snapshot == null) {
            throw new UserNotRegisteredException();
        }
    }

    private void checkIsUserFound(Optional<UserSnapshot> snapshot) {
        if(snapshot.isEmpty()) {
            throw new UserNotBoundException();
        }
    }

    private void checkPasswordsMatch(String password, UserSnapshot userSnapshot) {
        if( !authenticationProvider.checkPasswordsMatch(password, userSnapshot.getPassword())) {
            throw new PasswordMismatchException();
        }
    }

    private void verifyActivationToken(VerificationToken token) {
        if(verificationTokenExpired(token)) {
            throw new VerificationTokenExpiredException();
        }
    }

    private boolean verificationTokenExpired(VerificationToken token) {
        return token.isTokenExpired(now());
    }

    private Date now() {
        return Date.from(Instant.now());
    }

}
