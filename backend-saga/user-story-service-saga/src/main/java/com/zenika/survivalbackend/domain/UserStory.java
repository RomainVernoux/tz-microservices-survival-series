package com.zenika.survivalbackend.domain;

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
    @Enumerated(EnumType.STRING)
    private UserStoryStatus userStoryStatus;

    @Enumerated(EnumType.STRING)
    private UserStoryStatus updatingStatusFrom;

    private Date lastUpdate = new Date();

    public List<Event> changeStatus(UserStoryStatus status) {
        if (this.updatingStatusFrom != null)
            throw new IllegalStateException("User story is already being updated");

        this.updatingStatusFrom = this.userStoryStatus;
        this.userStoryStatus = status;
        return List.of(new UserStoryChangeStatusRequested(UUID.randomUUID(), this.id, this.projectId,
                this.updatingStatusFrom, status));
    }

    public void confirmUpdateStatus(boolean accepted, Date occurredOn) {
        if (occurredOn.before(this.lastUpdate))
            return;

        if (!accepted) {
            this.userStoryStatus = this.updatingStatusFrom;
        }

        this.updatingStatusFrom = null;
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

    public UserStoryStatus getUserStoryStatus() {
        return userStoryStatus;
    }

    public void setUserStoryStatus(UserStoryStatus userStoryStatus) {
        this.userStoryStatus = userStoryStatus;
    }

    public UserStoryStatus getUpdatingStatusFrom() {
        return updatingStatusFrom;
    }

    public void setUpdatingStatusFrom(UserStoryStatus updatingStatusFrom) {
        this.updatingStatusFrom = updatingStatusFrom;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
