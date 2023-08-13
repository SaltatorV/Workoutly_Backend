package com.workoutly.application.user.port.output;

import com.workoutly.application.user.event.UserActivatedEvent;
import com.workoutly.application.user.event.UserCreatedEvent;
import com.workoutly.application.user.event.UserUpdatedEvent;

public interface UserEventPublisher {
    void publish(UserCreatedEvent event);
    void publish(UserActivatedEvent event);
    void publish(UserUpdatedEvent event);
}
