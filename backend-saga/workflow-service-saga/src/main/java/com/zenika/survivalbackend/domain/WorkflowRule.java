package com.zenika.survivalbackend.domain;

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

    public List<Event> userStoryTransitions(UUID userStoryId, UserStoryStatus oldStatus, UserStoryStatus newStatus) {
        List<Event> events = new ArrayList<>();
        if (oldStatus == userStoryStatus) {
            currentNumberOfUserStories--;
        } else if (newStatus == userStoryStatus) {
            if (currentNumberOfUserStories >= maxNumberOfUserStories) {
                events.add(new WorkflowRuleProcessedUserStory(UUID.randomUUID(), userStoryId, this.projectId, this.userStoryStatus, false));
            } else {
                events.add(new WorkflowRuleProcessedUserStory(UUID.randomUUID(), userStoryId, this.projectId, this.userStoryStatus, true));
                currentNumberOfUserStories++;
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

    public static WorkflowRule allowingAllTransitions(UUID projectId, UserStoryStatus status) {
        WorkflowRule rule = new WorkflowRule();
        rule.projectId = projectId;
        rule.currentNumberOfUserStories = 0;
        rule.maxNumberOfUserStories = Integer.MAX_VALUE;
        rule.userStoryStatus = status;
        return rule;
    }
}
