package com.workoutly.email.services;

import com.workoutly.rabbitmq.message.EmailDataMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mail.SimpleMailMessage;

import static com.workoutly.email.services.utils.TestUtils.mapToString;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MailMessageCreatorTest {
    private MailMessageCreator creator;

    @BeforeEach
    public void setup() {
        creator = new MailMessageCreator();
    }

    @Test
    public void testMailCreate() {
        //given
        var message = createRabbitMessage();

        //when
        var mail = creator.create(message);

        //then
        assertMailIsValid(mail, message);
    }

    private EmailDataMessage createRabbitMessage() {
        return EmailDataMessage.create()
                .subject("test")
                .recipient("test")
                .content("test")
                .sender("test")
                .build();
    }

    private SimpleMailMessage createMail(EmailDataMessage message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(message.getRecipient());
        mailMessage.setSubject(message.getSubject());
        mailMessage.setFrom(message.getSender());
        mailMessage.setText(message.getContent());

        return mailMessage;
    }

    private void assertMailIsValid(SimpleMailMessage mail, EmailDataMessage message) {
        SimpleMailMessage expected = createMail(message);

        assertEquals(mapToString(expected), mapToString(mail));
    }
}
