package com.workoutly.email.services;

import com.workoutly.rabbitmq.message.EmailDataMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class MailMessageCreator {
    public SimpleMailMessage create(EmailDataMessage message)
    {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(message.getRecipient());
        mailMessage.setSubject(message.getSubject());
        mailMessage.setFrom(message.getSender());
        mailMessage.setText(message.getContent());

        return mailMessage;
    }
}
