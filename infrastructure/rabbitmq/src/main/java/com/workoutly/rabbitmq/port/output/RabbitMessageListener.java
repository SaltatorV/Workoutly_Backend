package com.workoutly.rabbitmq.port.output;

public interface RabbitMessageListener<T>{
    void receiveMessage(T message);
}
