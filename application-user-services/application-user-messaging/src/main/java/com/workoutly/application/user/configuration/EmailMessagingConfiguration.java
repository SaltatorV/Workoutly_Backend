package com.workoutly.application.user.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "email-configuration")
public class EmailMessagingConfiguration {
    private String sender;
    private String createSubject;
    private String createContent;
    private String activateSubject;
    private String activateContent;
    private String activateUrl;
    private String updateSubject;
}
