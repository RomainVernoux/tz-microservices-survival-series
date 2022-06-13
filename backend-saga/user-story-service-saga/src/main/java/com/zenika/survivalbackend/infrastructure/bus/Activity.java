package com.zenika.survivalbackend.infrastructure.bus;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Activity {
    @Id
    private UUID id;
    private ActivityDirection activityDirection;
    private String body;
    private boolean handled;
    private LocalDateTime handledDate;

    public Activity() {
    }

    public Activity(UUID id, ActivityDirection activityDirection, String body) {
        this.id = id;
        this.activityDirection = activityDirection;
        this.body = body;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public boolean isHandled() {
        return handled;
    }

    public void setHandled(boolean handled) {
        this.handled = handled;
    }

    public LocalDateTime getHandledDate() {
        return handledDate;
    }

    public void setHandledDate(LocalDateTime handledDate) {
        this.handledDate = handledDate;
    }
}
