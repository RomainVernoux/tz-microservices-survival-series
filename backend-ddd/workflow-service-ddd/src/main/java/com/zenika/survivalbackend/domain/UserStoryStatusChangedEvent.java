package com.zenika.survivalbackend.domain;

import java.util.UUID;

public class UserStoryStatusChangedEvent {
    private final UUID projectId;
    private final UUID userStoryId;
    private final UserStoryStatus oldStatus;
    private final UserStoryStatus newStatus;

    public UserStoryStatusChangedEvent(UUID projectId, UUID userStoryId, UserStoryStatus oldStatus, UserStoryStatus newStatus) {
        this.projectId = projectId;
        this.userStoryId = userStoryId;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
    }

    public UUID getProjectId() {
        return projectId;
    }

    public UUID getUserStoryId() {
        return userStoryId;
    }

    public UserStoryStatus getOldStatus() {
        return oldStatus;
    }

    public UserStoryStatus getNewStatus() {
        return newStatus;
    }
}
