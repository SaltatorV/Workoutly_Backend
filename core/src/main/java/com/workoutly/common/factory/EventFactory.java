package com.workoutly.common.factory;

public interface EventFactory<T,S> {
    T createEvent(S data);
}
