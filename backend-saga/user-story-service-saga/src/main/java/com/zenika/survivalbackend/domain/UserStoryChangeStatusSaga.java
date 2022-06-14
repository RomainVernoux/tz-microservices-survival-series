package com.zenika.survivalbackend.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class UserStoryChangeStatusSaga {
    @Id
    UUID id;
    UUID userStoryId;
    UUID projectId;
    @Enumerated(EnumType.STRING)
    UserStoryStatus oldStatus;
    @Enumerated(EnumType.STRING)
    UserStoryStatus newStatus;
    LocalDateTime requestDate;
    Boolean workflowRuleResponse;
    LocalDateTime workflowRuleResponseDate;

    public void applyWorkflowRuleAck(WorkflowRuleProcessedUserStory event) {
        this.workflowRuleResponse = event.isAccepted();
        this.workflowRuleResponseDate = LocalDateTime.now();
    }

    public boolean proceedWithChangeStatus() {
        return Boolean.TRUE.equals(this.workflowRuleResponse);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public UserStoryStatus getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(UserStoryStatus oldStatus) {
        this.oldStatus = oldStatus;
    }

    public UserStoryStatus getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(UserStoryStatus newStatus) {
        this.newStatus = newStatus;
    }

    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }

    public Boolean getWorkflowRuleResponse() {
        return workflowRuleResponse;
    }

    public void setWorkflowRuleResponse(Boolean workflowRuleResponse) {
        this.workflowRuleResponse = workflowRuleResponse;
    }

    public LocalDateTime getWorkflowRuleResponseDate() {
        return workflowRuleResponseDate;
    }

    public void setWorkflowRuleResponseDate(LocalDateTime workflowRuleResponseDate) {
        this.workflowRuleResponseDate = workflowRuleResponseDate;
    }

    public static UserStoryChangeStatusSaga from(UserStory userStory, UserStoryStatus newStatus) {
        UserStoryChangeStatusSaga saga = new UserStoryChangeStatusSaga();
        saga.id = UUID.randomUUID();
        saga.userStoryId = userStory.getId();
        saga.projectId = userStory.getProjectId();
        saga.oldStatus = userStory.getUserStoryStatus();
        saga.newStatus = newStatus;
        saga.requestDate = LocalDateTime.now();
        return saga;
    }


}
