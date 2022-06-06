package com.zenika.survivalbackend.model;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class WorkflowRule {
    @Id
    @GeneratedValue
    private UUID id;

    private UUID projectId;

    @Enumerated(EnumType.STRING)
    private UserStoryStatus userStoryStatus;

    private int maxNumberOfUserStories;

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

    public UserStoryStatus getUserStoryStatus() {
        return userStoryStatus;
    }

    public void setUserStoryStatus(UserStoryStatus userStoryStatus) {
        this.userStoryStatus = userStoryStatus;
    }

    public int getMaxNumberOfUserStories() {
        return maxNumberOfUserStories;
    }

    public void setMaxNumberOfUserStories(int maxNumberOfUserStories) {
        this.maxNumberOfUserStories = maxNumberOfUserStories;
    }
}
