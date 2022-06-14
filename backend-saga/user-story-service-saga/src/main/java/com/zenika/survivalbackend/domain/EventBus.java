package com.zenika.survivalbackend.domain;

public interface EventBus {

    void emit(Event event);

    void subscribe(Class<? extends Event> eventClass, EventHandler eventHandler);


}
