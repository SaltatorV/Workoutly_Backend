package com.workoutly.rabbitmq.factory;

public interface RabbitMessageFactory<S, T> {
    public S create(T t);
}
