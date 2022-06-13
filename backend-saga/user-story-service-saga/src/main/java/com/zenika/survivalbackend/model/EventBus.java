package com.zenika.survivalbackend.model;

import java.util.List;

public interface EventBus {

    void subscribe(Class<? extends Event> eventClass, EventHandler eventHandler);

    void emitAll(List<Event> events);
}
