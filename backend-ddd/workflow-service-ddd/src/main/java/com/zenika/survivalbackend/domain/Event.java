package com.zenika.survivalbackend.domain;

import java.util.Date;

public abstract class Event {
    private Date occurredOn = new Date();

    public Date getOccurredOn() {
        return occurredOn;
    }

    public void setOccurredOn(Date occurredOn) {
        this.occurredOn = occurredOn;
    }
}
