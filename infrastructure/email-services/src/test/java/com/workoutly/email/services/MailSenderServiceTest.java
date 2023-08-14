package com.workoutly.email.services;

import com.workoutly.rabbitmq.message.EmailDataMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class MailSenderServiceTest {

    @Mock
    private JavaMailSender sender;
    @InjectMocks
    private MailSenderService service;

    @Test
    public void testSendMail() {
        //given
        var mail = createMail();

        //when
        service.sendMailMessage(mail);

        //then
        verify(sender, times(1)).send(mail);
    }


    private SimpleMailMessage createMail() {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo("Test");
        mailMessage.setSubject("Test");
        mailMessage.setFrom("Test");
        mailMessage.setText("Test");

        return mailMessage;
    }
}
