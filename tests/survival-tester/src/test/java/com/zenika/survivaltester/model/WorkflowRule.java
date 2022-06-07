package com.zenika.survivaltester.model;

import java.util.UUID;

public class WorkflowRule {
    private UUID id;

    private UUID projectId;

    private String userStoryStatus;

    private int maxNumberOfUserStories;

    public WorkflowRule(UUID id, UUID projectId, String userStoryStatus, int maxNumberOfUserStories) {
        this.id = id;
        this.projectId = projectId;
        this.userStoryStatus = userStoryStatus;
        this.maxNumberOfUserStories = maxNumberOfUserStories;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getProjectId() {
        return projectId;
    }

    public void setProjectId(UUID projectId) {
        this.projectId = projectId;
    }

    public String getUserStoryStatus() {
        return userStoryStatus;
    }

    public void setUserStoryStatus(String userStoryStatus) {
        this.userStoryStatus = userStoryStatus;
    }

    public int getMaxNumberOfUserStories() {
        return maxNumberOfUserStories;
    }

    public void setMaxNumberOfUserStories(int maxNumberOfUserStories) {
        this.maxNumberOfUserStories = maxNumberOfUserStories;
    }
}
