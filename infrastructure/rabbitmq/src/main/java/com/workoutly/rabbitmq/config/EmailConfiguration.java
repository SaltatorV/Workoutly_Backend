package com.workoutly.rabbitmq.config;

import lombok.Data;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "email-mq")
public class EmailConfiguration {

    private String queue;
    private String exchange;
    private String routing;

    @Bean
    public Queue emailQueue(){
        return new Queue(queue);
    }

    @Bean
    public TopicExchange emailExchange(){
        return new TopicExchange(exchange);
    }

    @Bean
    public Binding emailBinding(){
        return BindingBuilder
                .bind(emailQueue())
                .to(emailExchange())
                .with(routing);
    }

}
