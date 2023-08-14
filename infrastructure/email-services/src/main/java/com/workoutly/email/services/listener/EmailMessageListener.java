package com.workoutly.email.services.listener;

import com.workoutly.email.services.MailMessageCreator;
import com.workoutly.email.services.MailSenderService;
import com.workoutly.rabbitmq.message.EmailDataMessage;
import com.workoutly.rabbitmq.port.output.RabbitMessageListener;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "${email-mq.queue}")
@RequiredArgsConstructor
public class EmailMessageListener implements RabbitMessageListener<EmailDataMessage> {
    private final MailSenderService mailSenderService;
    private final MailMessageCreator mailMessageCreator;

    @Override
    public void receiveMessage(EmailDataMessage message) {
        SimpleMailMessage mailMessage = mailMessageCreator.create(message);
        mailSenderService.sendMailMessage(mailMessage);
    }
}
