package com.workoutly.application.user.publisher;

import com.workoutly.application.user.event.UserActivatedEvent;
import com.workoutly.application.user.event.UserCreatedEvent;
import com.workoutly.application.user.event.UserUpdatedEvent;

import com.workoutly.application.user.mapper.EmailMessageMapper;
import com.workoutly.application.user.port.output.UserEventPublisher;

import com.workoutly.rabbitmq.config.EmailConfiguration;
import com.workoutly.rabbitmq.message.EmailDataMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEventPublisherImpl implements UserEventPublisher {

    private final EmailConfiguration emailConfiguration;
    private final RabbitTemplate rabbitTemplate;
    private final EmailMessageMapper mapper;

    @Override
    public void publish(UserCreatedEvent event) {
        send(mapper.mapUserCreatedEventToEmailMessage(event));
    }

    @Override
    public void publish(UserActivatedEvent event) {
        send(mapper.mapUserActivatedEventToEmailMessage(event));
    }

    @Override
    public void publish(UserUpdatedEvent event) {
        send(mapper.mapUserUpdatedEventToEmailMessage(event));
    }

    private void send(EmailDataMessage message) {
        rabbitTemplate.convertAndSend(emailConfiguration.getExchange(), emailConfiguration.getRouting(),
                message);
    }
}
