package com.zenika.survivaltester.model;

import java.util.UUID;

public class UserStory {

    private UUID id;
    private UUID projectId;
    private String title;
    private String description;
    private String userStoryStatus;

    public UserStory() {
    }

    public UserStory(UUID id, UUID projectId, String title, String description, String userStoryStatus) {
        this.id = id;
        this.projectId = projectId;
        this.title = title;
        this.description = description;
        this.userStoryStatus = userStoryStatus;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserStoryStatus() {
        return userStoryStatus;
    }

    public void setUserStoryStatus(String userStoryStatus) {
        this.userStoryStatus = userStoryStatus;
    }
}
