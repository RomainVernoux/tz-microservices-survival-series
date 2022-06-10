package com.zenika.survivalbackend.model;

import java.util.Date;
import java.util.UUID;

public class UserStoryEvent {
    private String eventType;
    private Date occuredOn;
    private UUID userStoryId;

    public UserStoryEvent() {
    }

    public UserStoryEvent(String eventType, Date occuredOn, UUID userStoryId) {
        this.eventType = eventType;
        this.occuredOn = occuredOn;
        this.userStoryId = userStoryId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Date getOccuredOn() {
        return occuredOn;
    }

    public void setOccuredOn(Date occuredOn) {
        this.occuredOn = occuredOn;
    }

    public UUID getUserStoryId() {
        return userStoryId;
    }

    public void setUserStoryId(UUID userStoryId) {
        this.userStoryId = userStoryId;
    }

    @Override
    public String toString() {
        return "UserStoryEvent{" +
                "eventType='" + eventType + '\'' +
                ", occuredOn=" + occuredOn +
                ", userStoryId=" + userStoryId +
                '}';
    }
}
