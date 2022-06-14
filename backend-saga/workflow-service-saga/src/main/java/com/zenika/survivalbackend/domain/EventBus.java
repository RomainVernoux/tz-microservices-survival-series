package com.zenika.survivalbackend.domain;

import java.util.List;

public interface EventBus {

    <T extends Event> void subscribe(Class<T> eventClass, EventHandler<T> eventHandler);

    void emitAll(List<Event> events);
}
