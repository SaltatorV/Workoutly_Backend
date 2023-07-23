package com.workoutly.application.user.configuration;


import com.workoutly.application.user.mock.MockUserApplicationService;
import com.workoutly.application.user.port.input.UserApplicationService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class BeanConfiguration {

    @Bean
    public UserApplicationService userApplicationService() {
        return new MockUserApplicationService();
    }
}
