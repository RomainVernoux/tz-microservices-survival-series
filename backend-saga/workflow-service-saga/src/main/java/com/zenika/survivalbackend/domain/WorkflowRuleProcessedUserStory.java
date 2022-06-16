package com.zenika.survivalbackend.domain;

import java.util.UUID;

public class WorkflowRuleProcessedUserStory extends Event {
    private UUID userStoryId;
    private UUID projectId;
    private UserStoryStatus status;

    private boolean accepted;

    public WorkflowRuleProcessedUserStory(UUID id, UUID userStoryId, UUID projectId, UserStoryStatus status, boolean accepted) {
        super(id);
        this.userStoryId = userStoryId;
        this.projectId = projectId;
        this.status = status;
        this.accepted = accepted;
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

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    @Override
    public String toString() {
        return "WorkflowRuleProcessedUserStory{" +
                "userStoryId=" + userStoryId +
                ", projectId=" + projectId +
                ", status=" + status +
                ", accepted=" + accepted +
                '}';
    }
}
