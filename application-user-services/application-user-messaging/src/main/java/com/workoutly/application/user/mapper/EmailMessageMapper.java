package com.workoutly.application.user.mapper;

import com.workoutly.application.user.configuration.EmailMessagingConfiguration;
import com.workoutly.application.user.event.UserActivatedEvent;
import com.workoutly.application.user.event.UserCreatedEvent;
import com.workoutly.application.user.event.UserUpdatedEvent;
import com.workoutly.rabbitmq.message.EmailDataMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailMessageMapper {
    private final EmailMessagingConfiguration configuration;

    public EmailDataMessage mapUserCreatedEventToEmailMessage(UserCreatedEvent event) {
        return EmailDataMessage.create()
                .recipient(event.getSnapshot().getEmail())
                .sender(configuration.getSender())
                .subject(configuration.getCreateSubject())
                .content(configureCreatedUserContent(event))
                .build();
    }

    public EmailDataMessage mapUserActivatedEventToEmailMessage(UserActivatedEvent event) {
        return EmailDataMessage.create()
                .recipient(event.getSnapshot().getEmail())
                .sender(configuration.getSender())
                .subject(configuration.getActivateSubject())
                .content(configuration.getActivateContent())
                .build();
    }

    public EmailDataMessage mapUserUpdatedEventToEmailMessage(UserUpdatedEvent event) {
        return EmailDataMessage.create()
                .recipient(event.getSnapshot().getEmail())
                .sender(configuration.getSender())
                .subject(configuration.getUpdateSubject())
                .content(event.getMessage())
                .build();
    }

    private String configureCreatedUserContent(UserCreatedEvent event) {
        return String.format(configuration.getCreateContent(), event.getSnapshot().getToken().getToken());
    }
}
