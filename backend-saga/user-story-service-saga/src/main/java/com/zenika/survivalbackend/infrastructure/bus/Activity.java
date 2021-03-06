package com.zenika.survivalbackend.infrastructure.bus;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Activity {
    @Id
    private UUID id;

    private UUID eventId;
    @Enumerated(EnumType.STRING)
    private ActivityDirection activityDirection;
    private String body;
    private LocalDateTime occurredOn = LocalDateTime.now();
    private Boolean handled = Boolean.FALSE;
    private LocalDateTime handledDate;

    public Activity() {
    }

    public Activity(UUID id, UUID eventId, ActivityDirection activityDirection, String body) {
        this.id = id;
        this.eventId = eventId;
        this.activityDirection = activityDirection;
        this.body = body;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getEventId() {
        return eventId;
    }

    public void setEventId(UUID eventId) {
        this.eventId = eventId;
    }

    public ActivityDirection getActivityDirection() {
        return activityDirection;
    }

    public void setActivityDirection(ActivityDirection activityDirection) {
        this.activityDirection = activityDirection;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getOccurredOn() {
        return occurredOn;
    }

    public void setOccurredOn(LocalDateTime occurredOn) {
        this.occurredOn = occurredOn;
    }

    public Boolean getHandled() {
        return handled;
    }

    public void setHandled(Boolean handled) {
        this.handled = handled;
    }

    public LocalDateTime getHandledDate() {
        return handledDate;
    }

    public void setHandledDate(LocalDateTime handledDate) {
        this.handledDate = handledDate;
    }
}
