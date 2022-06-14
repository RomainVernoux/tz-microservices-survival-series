package com.zenika.survivalbackend.domain;

public interface EventHandler<T extends Event> {
    void handle(T event);
}
