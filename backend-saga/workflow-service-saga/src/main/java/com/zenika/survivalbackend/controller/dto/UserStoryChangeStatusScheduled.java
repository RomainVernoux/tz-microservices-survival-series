package com.zenika.survivalbackend.controller.dto;

import com.zenika.survivalbackend.model.Event;
import com.zenika.survivalbackend.model.workflow.UserStoryStatus;

import java.util.UUID;

public class UserStoryChangeStatusScheduled extends Event {

    private UUID userStoryId;
    private UserStoryStatus status;

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
