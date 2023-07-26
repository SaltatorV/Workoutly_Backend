package com.workoutly.application.user.configuration;


import com.workoutly.application.user.mock.MockUserApplicationService;
import com.workoutly.application.user.port.input.UserApplicationService;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@TestConfiguration
public class BeanConfiguration {

    @Bean
    public UserApplicationService userApplicationService() {
        return new MockUserApplicationService();
    }
    @Bean
    public Validator validator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }
}
