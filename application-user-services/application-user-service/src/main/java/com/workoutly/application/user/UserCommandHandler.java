package com.workoutly.application.user;

import com.workoutly.application.user.VO.UserSnapshot;
import com.workoutly.application.user.auth.TokenService;
import com.workoutly.application.user.dto.command.ActivationUserCommand;
import com.workoutly.application.user.dto.command.AuthenticationCommand;
import com.workoutly.application.user.dto.command.RegisterUserCommand;
import com.workoutly.application.user.event.UserActivatedEvent;
import com.workoutly.application.user.event.UserCreatedEvent;
import com.workoutly.application.user.exception.UserNotBoundException;
import com.workoutly.application.user.exception.UserNotRegisteredException;
import com.workoutly.application.user.exception.UserNotUniqueException;
import com.workoutly.application.user.exception.VerificationTokenExpiredException;
import com.workoutly.application.user.mapper.UserDataMapper;
import com.workoutly.application.user.port.output.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

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


    public String authenticate(AuthenticationCommand authenticationCommand) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationCommand.getUsername(),
                        authenticationCommand.getPassword()
                )
        );
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        return tokenService.generateNewToken(userDetails);
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
