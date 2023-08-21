package com.workoutly.application.user.mapper;

import com.workoutly.application.user.VO.UserId;
import com.workoutly.application.user.VO.UserRole;
import com.workoutly.application.user.VO.UserSnapshot;
import com.workoutly.application.user.VerificationToken;
import com.workoutly.application.user.configuration.EmailMessagingConfiguration;
import com.workoutly.application.user.event.UserActivatedEvent;
import com.workoutly.application.user.event.UserCreatedEvent;
import com.workoutly.application.user.event.UserUpdatedEvent;
import com.workoutly.application.user.mock.MockEmailMessagingConfiguration;
import com.workoutly.rabbitmq.message.EmailDataMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.workoutly.application.user.VO.UserSnapshot.Builder.anUserSnapshot;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmailMessageMapperTest {

    private EmailMessagingConfiguration configuration;
    private EmailMessageMapper mapper;

    @BeforeEach
    public void setup() {
        configuration = new MockEmailMessagingConfiguration();
        mapper = new EmailMessageMapper(configuration);
    }


    @Test
    public void testMapUserCreatedEvent() {
        //given
        var event = createUserCreatedEvent();

        //when
        var message = mapper.mapUserCreatedEventToEmailMessage(event);

        //then
        assertIsMessageValid(message, event);
    }

    @Test
    public void testMapUserActivatedEvent() {
        //given
        var event = createUserActivatedEvent();

        //when
        var message = mapper.mapUserActivatedEventToEmailMessage(event);

        //then
        assertIsMessageValid(message, event);
    }

    @Test
    public void testMapUserUpdatedEvent() {
        //given
        var event = createUserUpdatedEvent();

        //when
        var message = mapper.mapUserUpdatedEventToEmailMessage(event);

        //then
        assertIsMessageValid(message, event);
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

    private void assertIsMessageValid(EmailDataMessage message, UserCreatedEvent event) {
        EmailDataMessage expectedMessage = EmailDataMessage.create()
                .recipient(event.getSnapshot().getEmail())
                .sender(configuration.getSender())
                .subject(configuration.getCreateSubject())
                .content(configureContent(event))
                .build();

        assertEquals(expectedMessage, message);
    }

    private String configureContent(UserCreatedEvent event) {
        return String.format(configuration.getCreateContent(), event.getSnapshot().getToken().getToken());
    }

    private void assertIsMessageValid(EmailDataMessage message, UserActivatedEvent event) {
        EmailDataMessage expectedMessage = EmailDataMessage.create()
                .recipient(event.getSnapshot().getEmail())
                .sender(configuration.getSender())
                .subject(configuration.getActivateSubject())
                .content(configuration.getActivateContent())
                .build();

        assertEquals(expectedMessage, message);
    }

    private void assertIsMessageValid(EmailDataMessage message, UserUpdatedEvent event) {
        EmailDataMessage expectedMessage = EmailDataMessage.create()
                .recipient(event.getSnapshot().getEmail())
                .sender(configuration.getSender())
                .subject(configuration.getUpdateSubject())
                .content(event.getMessage())
                .build();

        assertEquals(expectedMessage, message);
    }
}
