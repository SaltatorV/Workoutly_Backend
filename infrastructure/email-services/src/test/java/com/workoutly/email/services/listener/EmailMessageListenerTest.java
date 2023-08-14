package com.workoutly.email.services.listener;

import com.workoutly.email.services.MailMessageCreator;
import com.workoutly.email.services.MailSenderService;
import com.workoutly.rabbitmq.message.EmailDataMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmailMessageListenerTest {

    @Mock
    private MailMessageCreator creator;
    @Mock
    private MailSenderService service;
    @InjectMocks
    private EmailMessageListener listener;

    @Test
    public void testReceiveMessage() {
        //given
        var message = createRabbitMessage();
        var mail = createMail(message);

        doReturn(mail)
                .when(creator)
                .create(message);

        //when
        listener.receiveMessage(message);

        //then
        verify(creator, times(1)).create(message);
        verify(service, times(1)).sendMailMessage(mail);
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
}
