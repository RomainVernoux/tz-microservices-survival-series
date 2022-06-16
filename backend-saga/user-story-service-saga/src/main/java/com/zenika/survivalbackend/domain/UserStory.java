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

    private boolean updatingStatus;

    private Date lastStatusUpdate = new Date();

    public List<Event> changeStatus(UserStoryStatus status) {
        if (this.updatingStatus)
            throw new IllegalStateException("User story is already being updated");

        this.updatingStatus = true;
        return List.of(new UserStoryChangeStatusScheduled(UUID.randomUUID(), this.id, this.projectId, this.userStoryStatus, status));
    }

    public void confirmUpdateStatus(boolean accepted, UserStoryStatus status, Date occurredOn) {
        if (accepted && occurredOn.after(this.lastStatusUpdate)) {
            this.userStoryStatus = status;
            this.updatingStatus = false;
        }
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

    public boolean isUpdatingStatus() {
        return updatingStatus;
    }

    public void setUpdatingStatus(boolean updatingStatus) {
        this.updatingStatus = updatingStatus;
    }

    public Date getLastStatusUpdate() {
        return lastStatusUpdate;
    }

    public void setLastStatusUpdate(Date lastStatusUpdate) {
        this.lastStatusUpdate = lastStatusUpdate;
    }
}
