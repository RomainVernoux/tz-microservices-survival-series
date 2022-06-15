package com.zenika.survivalbackend.domain;

public interface EventBus {
    void emit(Event event);
}
