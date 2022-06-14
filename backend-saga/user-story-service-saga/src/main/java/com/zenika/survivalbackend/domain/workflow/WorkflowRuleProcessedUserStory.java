package com.zenika.survivalbackend.domain.workflow;

import com.zenika.survivalbackend.domain.Event;
import com.zenika.survivalbackend.domain.userstory.UserStoryStatus;

import java.util.UUID;

public class WorkflowRuleProcessedUserStory extends Event {
    private UUID userStoryId;
    private UUID projectId;
    private UserStoryStatus status;

    private boolean accepted;

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

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
}
