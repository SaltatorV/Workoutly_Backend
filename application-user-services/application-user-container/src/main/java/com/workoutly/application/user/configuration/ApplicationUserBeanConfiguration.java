package com.workoutly.application.user.configuration;

import com.workoutly.application.user.UserDomainService;
import com.workoutly.application.user.UserDomainServiceImpl;
import com.workoutly.application.user.auth.AuthenticationUserDetailsService;
import com.workoutly.application.user.port.output.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationUserBeanConfiguration {

    @Bean
    public UserDomainService userDomainService() {
        return new UserDomainServiceImpl();
    }

    @Bean
    UserDetailsService userDetailsService(UserRepository userRepository) {
        return new AuthenticationUserDetailsService(userRepository);
    }
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
