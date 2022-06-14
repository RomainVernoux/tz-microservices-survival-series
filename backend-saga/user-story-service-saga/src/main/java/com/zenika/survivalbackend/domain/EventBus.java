package com.zenika.survivalbackend.domain;

public interface EventBus {

    void subscribe(Class<? extends Event> eventClass, EventHandler eventHandler);

    void emit(Event event);
}
