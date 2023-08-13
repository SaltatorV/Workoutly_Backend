package com.workoutly.application.user.publisher;


import com.workoutly.application.user.VO.UserId;
import com.workoutly.application.user.VO.UserRole;
import com.workoutly.application.user.VO.UserSnapshot;
import com.workoutly.application.user.VerificationToken;
import com.workoutly.application.user.event.UserActivatedEvent;
import com.workoutly.application.user.event.UserCreatedEvent;
import com.workoutly.application.user.event.UserUpdatedEvent;
import com.workoutly.application.user.mapper.EmailMessageMapper;
import com.workoutly.rabbitmq.config.EmailConfiguration;
import com.workoutly.rabbitmq.message.EmailDataMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.UUID;

import static com.workoutly.application.user.VO.UserSnapshot.Builder.anUserSnapshot;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserEventPublisherImplTest {

    @Mock
    private EmailConfiguration emailConfiguration;
    @Mock
    private RabbitTemplate rabbitTemplate;
    @Mock
    private EmailMessageMapper mapper;
    @InjectMocks
    private UserEventPublisherImpl publisher;

    @Test
    public void testPublishUserCreatedEvent() {
        //given
        var event = createUserCreatedEvent();
        var message = createSampleMessage();

        doReturn(message)
                .when(mapper)
                .mapUserCreatedEventToEmailMessage(event);

        doReturn(createExchange())
                .when(emailConfiguration)
                .getExchange();

        doReturn(createRouting())
                .when(emailConfiguration)
                .getRouting();

        //when
        publisher.publish(event);

        //then
        verify(mapper, times(1)).mapUserCreatedEventToEmailMessage(event);
        verifySendMessage(message);
    }

    @Test
    public void testPublishUserActivatedEvent() {
        //given
        var event = createUserActivatedEvent();
        var message = createSampleMessage();

        doReturn(message)
                .when(mapper)
                .mapUserActivatedEventToEmailMessage(event);

        doReturn(createExchange())
                .when(emailConfiguration)
                .getExchange();

        doReturn(createRouting())
                .when(emailConfiguration)
                .getRouting();

        //when
        publisher.publish(event);

        //then
        verify(mapper, times(1)).mapUserActivatedEventToEmailMessage(event);
        verifySendMessage(message);
    }

    @Test
    public void testPublishUserUpdatedEvent() {
        //given
        var event = createUserUpdatedEvent();
        var message = createSampleMessage();

        doReturn(message)
                .when(mapper)
                .mapUserUpdatedEventToEmailMessage(event);

        doReturn(createExchange())
                .when(emailConfiguration)
                .getExchange();

        doReturn(createRouting())
                .when(emailConfiguration)
                .getRouting();

        //when
        publisher.publish(event);

        //then
        verify(mapper, times(1)).mapUserUpdatedEventToEmailMessage(event);
        verifySendMessage(message);
    }


    private UserCreatedEvent createUserCreatedEvent() {
        return new UserCreatedEvent(createSnapshot());
    }

    private UserActivatedEvent createUserActivatedEvent() {
        return new UserActivatedEvent(createSnapshot());
    }

    private UserUpdatedEvent createUserUpdatedEvent() {
        return new UserUpdatedEvent(createSnapshot(), "Password has been changed.");
    }

    private EmailDataMessage createSampleMessage() {
        return EmailDataMessage.create()
                .sender("test")
                .recipient("test")
                .subject("test")
                .content("test")
                .build();
    }

    private String createExchange() {
        return "test";
    }

    private String createRouting() {
        return "test";
    }

    private UserSnapshot createSnapshot() {
        return anUserSnapshot()
                .withUserId(new UserId(UUID.randomUUID()))
                .withUsername("Test")
                .withPassword("password")
                .withEmail("example@example.to")
                .withIsEnabled(false)
                .withRole(UserRole.COMMON_USER)
                .withToken(VerificationToken.generateToken().createTokenSnapshot())
                .build();
    }

    private void verifySendMessage(EmailDataMessage message) {
        verify(rabbitTemplate, times(1)).convertAndSend(createExchange(), createRouting(), message);
        verify(emailConfiguration, times(1)).getRouting();
        verify(emailConfiguration, times(1)).getExchange();
    }
}
