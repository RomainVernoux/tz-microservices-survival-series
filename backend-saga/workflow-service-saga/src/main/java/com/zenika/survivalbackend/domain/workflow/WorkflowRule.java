package com.zenika.survivalbackend.domain.workflow;

import com.zenika.survivalbackend.domain.Event;
import com.zenika.survivalbackend.domain.userstory.UserStoryStatus;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
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

    public List<Event> acceptUserStory(UUID userStoryId, WorkflowRule removeWorkflowRule) {
        List<Event> events = new ArrayList<>();
        boolean rejectUserStory = this.maxNumberOfUserStories > 0 &&
                this.currentNumberOfUserStories >= this.maxNumberOfUserStories;

        if (rejectUserStory) {
            events.add(new WorkflowRuleProcessedUserStory(UUID.randomUUID(), userStoryId, this.projectId, this.userStoryStatus, false));
        } else {
            this.currentNumberOfUserStories = this.currentNumberOfUserStories + 1;
            events.add(new WorkflowRuleProcessedUserStory(UUID.randomUUID(), userStoryId, this.projectId, this.userStoryStatus, true));
            if (removeWorkflowRule.maxNumberOfUserStories > 0) {
                removeWorkflowRule.currentNumberOfUserStories = removeWorkflowRule.currentNumberOfUserStories - 1;
            }
        }
        return events;
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

    public int getCurrentNumberOfUserStories() {
        return currentNumberOfUserStories;
    }

    public void setCurrentNumberOfUserStories(int currentNumberOfUserStories) {
        this.currentNumberOfUserStories = currentNumberOfUserStories;
    }
}
