package com.workoutly.application.user.auth;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

class AuthenticationTokenFilter extends OncePerRequestFilter {
    private static final String BEARER = "Bearer ";

    private final UserDetailsService userDetailsService;
    private final TokenService tokenService;

    AuthenticationTokenFilter(UserDetailsService userDetailsService, TokenService tokenService) {
        this.userDetailsService = userDetailsService;
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional.ofNullable(
                        request.getHeader(HttpHeaders.AUTHORIZATION)
                ).filter(header -> isBearer(header))
                .map(header -> cutHeader(header))
                .ifPresent(token -> {
                    if (SecurityContextHolder.getContext().getAuthentication() != null) {
                        return;
                    }
                    String username = tokenService.getUsernameFromToken(token);
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    if (tokenService.isValidForUser(token, userDetails)) {
                        var authentication = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                });
        filterChain.doFilter(request, response);
    }

    private boolean isBearer(String header) {
        return header.startsWith(BEARER);
    }

    private String cutHeader(String header) {
        return header.substring(BEARER.length());
    }

}
