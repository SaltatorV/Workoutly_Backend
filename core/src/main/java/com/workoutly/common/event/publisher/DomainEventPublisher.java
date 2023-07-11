package com.workoutly.common.event.publisher;

import com.workoutly.common.event.DomainEvent;

public interface DomainEventPublisher<T extends DomainEvent> {

    void publish(T domainEvent);
}
