package com.zenika.survivalbackend.model;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class WorkflowRule {
    @Id
    @GeneratedValue
    private UUID id;

    private UUID projectId;

    @Version
    int version;

    @Enumerated(EnumType.STRING)
    private UserStoryStatus userStoryStatus;

    private int maxNumberOfUserStories;

    private UUID lock;

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

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
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

    public UUID getLock() {
        return lock;
    }

    public void setLock(UUID lock) {
        this.lock = lock;
    }
}
