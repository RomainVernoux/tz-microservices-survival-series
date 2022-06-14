package com.zenika.survivalbackend.domain;

import java.util.Date;
import java.util.UUID;

public class Event {
    private UUID id;

    private Date occurredOn = new Date();

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

    public Date getOccurredOn() {
        return occurredOn;
    }

    public void setOccurredOn(Date occurredOn) {
        this.occurredOn = occurredOn;
    }
}
