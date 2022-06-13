package com.zenika.survivalbackend.model.userstory;

import com.zenika.survivalbackend.model.Event;

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

    private Boolean changingStatus;

    @Enumerated(EnumType.STRING)
    private UserStoryStatus userStoryStatus;


    public List<Event> changeStatus(UserStoryStatus status) {
        this.changingStatus = true;
        return List.of(new UserStoryChangeStatusScheduled(this.id, status));
    }
}
