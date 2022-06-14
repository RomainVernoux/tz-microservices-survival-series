package com.zenika.survivalbackend.domain.userstory;

import com.zenika.survivalbackend.domain.Event;

import java.util.UUID;

public class UserStoryChangeStatusScheduled extends Event {

    private UUID userStoryId;
    private UUID projectId;
    private UserStoryStatus previousStatus;
    private UserStoryStatus newStatus;

    public UserStoryChangeStatusScheduled(UUID id, UUID userStoryId, UUID projectId, UserStoryStatus previousStatus, UserStoryStatus newStatus) {
        super(id);
        this.userStoryId = userStoryId;
        this.projectId = projectId;
        this.previousStatus = previousStatus;
        this.newStatus = newStatus;
    }

    public UUID getUserStoryId() {
        return userStoryId;
    }

    public void setUserStoryId(UUID userStoryId) {
        this.userStoryId = userStoryId;
    }

    public UUID getProjectId() {
        return projectId;
    }

    public void setProjectId(UUID projectId) {
        this.projectId = projectId;
    }

    public UserStoryStatus getPreviousStatus() {
        return previousStatus;
    }

    public void setPreviousStatus(UserStoryStatus previousStatus) {
        this.previousStatus = previousStatus;
    }

    public UserStoryStatus getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(UserStoryStatus newStatus) {
        this.newStatus = newStatus;
    }
}
