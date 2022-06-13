package com.zenika.survivalbackend.model;

import java.util.UUID;

public class Event {
    private UUID id;

    public Event() {
    }

    public Event(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
