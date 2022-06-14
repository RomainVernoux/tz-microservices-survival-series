package com.zenika.survivalbackend.model;

public interface EventHandler<T extends Event> {
    void handle(T event);
}
