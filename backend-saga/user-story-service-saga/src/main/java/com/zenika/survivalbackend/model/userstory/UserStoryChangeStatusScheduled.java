package com.zenika.survivalbackend.model.userstory;

import com.zenika.survivalbackend.model.Event;

import java.util.UUID;

public class UserStoryChangeStatusScheduled extends Event {

    private UUID userStoryId;
    private UserStoryStatus status;

    public UserStoryChangeStatusScheduled(UUID userStoryId, UserStoryStatus status) {
        this.userStoryId = userStoryId;
        this.status = status;
    }

    public UUID getUserStoryId() {
        return userStoryId;
    }

    public void setUserStoryId(UUID userStoryId) {
        this.userStoryId = userStoryId;
    }

    public UserStoryStatus getStatus() {
        return status;
    }

    public void setStatus(UserStoryStatus status) {
        this.status = status;
    }
}
