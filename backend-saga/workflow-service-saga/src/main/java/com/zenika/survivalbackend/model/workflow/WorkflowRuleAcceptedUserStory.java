package com.zenika.survivalbackend.model.workflow;

import com.zenika.survivalbackend.model.Event;
import com.zenika.survivalbackend.model.userstory.UserStoryStatus;

import java.util.UUID;

public class WorkflowRuleAcceptedUserStory extends Event {
    private UUID userStoryId;
    private UUID projectId;
    private UserStoryStatus status;

    public WorkflowRuleAcceptedUserStory(UUID id, UUID userStoryId, UUID projectId, UserStoryStatus status) {
        super(id);
        this.userStoryId = userStoryId;
        this.projectId = projectId;
        this.status = status;
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

    public UserStoryStatus getStatus() {
        return status;
    }

    public void setStatus(UserStoryStatus status) {
        this.status = status;
    }
}
