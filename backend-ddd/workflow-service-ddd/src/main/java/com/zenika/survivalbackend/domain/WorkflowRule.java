package com.zenika.survivalbackend.domain;

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
    private int currentNumberOfUserStories;

    protected WorkflowRule() {
    }

    public void userStoryTransitions(UserStoryStatus oldStatus, UserStoryStatus newStatus) {
        if (oldStatus == userStoryStatus) {
            currentNumberOfUserStories--;
        } else if (newStatus == userStoryStatus) {
            if (currentNumberOfUserStories >= maxNumberOfUserStories)
                throw new IllegalArgumentException("The maximum number of stories in status has been reached");
            currentNumberOfUserStories++;
        }
    }

    public UUID getId() {
        return id;
    }

    public UUID getProjectId() {
        return projectId;
    }

    public UserStoryStatus getUserStoryStatus() {
        return userStoryStatus;
    }

    public int getMaxNumberOfUserStories() {
        return maxNumberOfUserStories;
    }

    public int getCurrentNumberOfUserStories() {
        return currentNumberOfUserStories;
    }
}
