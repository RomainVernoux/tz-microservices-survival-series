package com.zenika.survivalbackend.exposition;

import com.zenika.survivalbackend.domain.Event;
import com.zenika.survivalbackend.domain.UserStoryStatus;

import java.util.UUID;

public class UserStoryChangeStatusRequested extends Event {

    private UUID userStoryId;
    private UUID projectId;
    private UserStoryStatus oldStatus;
    private UserStoryStatus newStatus;

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

    public UserStoryStatus getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(UserStoryStatus oldStatus) {
        this.oldStatus = oldStatus;
    }

    public UserStoryStatus getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(UserStoryStatus newStatus) {
        this.newStatus = newStatus;
    }
}
