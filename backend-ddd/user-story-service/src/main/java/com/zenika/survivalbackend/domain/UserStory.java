package com.zenika.survivalbackend.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
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

    protected UserStory() {

    }

    public List<Event> changeStatus(UserStoryStatus newStatus) {
        UserStoryStatus oldStatus = userStoryStatus;
        userStoryStatus = newStatus;
        return List.of(new UserStoryStatusChangedEvent(projectId, id, oldStatus, newStatus));
    }

    public UUID getId() {
        return id;
    }

    public UUID getProjectId() {
        return projectId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public UserStoryStatus getUserStoryStatus() {
        return userStoryStatus;
    }

}
