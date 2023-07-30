package com.workoutly.application.user.configuration;

import com.workoutly.application.user.UserDomainService;
import com.workoutly.application.user.UserDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public UserDomainService userDomainService() {
        return new UserDomainServiceImpl();
    }
}
