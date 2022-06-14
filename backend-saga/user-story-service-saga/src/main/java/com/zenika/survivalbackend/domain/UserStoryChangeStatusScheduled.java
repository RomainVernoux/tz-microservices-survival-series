package com.zenika.survivalbackend.domain;

import java.util.UUID;

public class UserStoryChangeStatusScheduled extends Event {

    private UUID userStoryId;
    private UUID projectId;
    private UserStoryStatus oldStatus;
    private UserStoryStatus newStatus;

    public UserStoryChangeStatusScheduled(UUID id, UUID userStoryId, UUID projectId, UserStoryStatus oldStatus, UserStoryStatus newStatus) {
        super(id);
        this.projectId = projectId;
        this.userStoryId = userStoryId;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
    }

    public UUID getUserStoryId() {
        return userStoryId;
    }

    public UUID getProjectId() {
        return projectId;
    }

    public UserStoryStatus getOldStatus() {
        return oldStatus;
    }

    public UserStoryStatus getNewStatus() {
        return newStatus;
    }

}
