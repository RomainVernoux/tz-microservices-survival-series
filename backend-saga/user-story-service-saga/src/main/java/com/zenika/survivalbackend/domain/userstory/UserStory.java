package com.zenika.survivalbackend.domain.userstory;

import com.zenika.survivalbackend.domain.Event;
import com.zenika.survivalbackend.domain.workflow.WorkflowRuleProcessedUserStory;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
public class UserStory {
    @Id
    private UUID id;

    private UUID projectId;

    private String title;

    private String description;
    private Boolean changingStatus = Boolean.FALSE;

    @Enumerated(EnumType.STRING)
    private UserStoryStatus userStoryStatus;

    private Date lastStatusUpdate = new Date();


    public List<Event> changeStatus(UserStoryStatus status) {
        if (this.changingStatus)
            throw new IllegalStateException("The user story is already in the process of changing its status");

        this.changingStatus = true;
        return List.of(new UserStoryChangeStatusScheduled(UUID.randomUUID(), this.id, this.projectId, this.userStoryStatus, status));
    }

    public void processWorkflowRuleConstraints(WorkflowRuleProcessedUserStory event) {
        if (event.getOccurredOn().before(this.lastStatusUpdate))
            return;

        this.changingStatus = false;
        this.userStoryStatus = event.getStatus();
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

    public Boolean getChangingStatus() {
        return changingStatus;
    }

    public void setChangingStatus(Boolean changingStatus) {
        this.changingStatus = changingStatus;
    }

    public UserStoryStatus getUserStoryStatus() {
        return userStoryStatus;
    }

    public void setUserStoryStatus(UserStoryStatus userStoryStatus) {
        this.userStoryStatus = userStoryStatus;
    }

    public Date getLastStatusUpdate() {
        return lastStatusUpdate;
    }

    public void setLastStatusUpdate(Date lastStatusUpdate) {
        this.lastStatusUpdate = lastStatusUpdate;
    }
}
