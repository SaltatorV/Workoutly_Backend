package com.workoutly.application.user.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationProvider {
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public String getAuthenticatedUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public UserDetails createUserDetails(UsernamePasswordAuthenticationToken authenticationToken) {
        return (UserDetails) authenticationManager.authenticate(authenticationToken).getPrincipal();
    }

    public boolean checkPasswordsMatch(String commandPassword, String currentPassword) {
        return passwordEncoder.matches(commandPassword, currentPassword);
    }

    public String encodePassword(String newPassword) {
        return passwordEncoder.encode(newPassword);
    }
}
