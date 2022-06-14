package com.zenika.survivalbackend.domain;

public interface EventBus {

    <T extends Event> void subscribe(Class<T> eventClass, EventHandler<T> eventHandler);

    void emit(Event event);
}
