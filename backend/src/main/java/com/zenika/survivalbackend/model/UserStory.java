package com.zenika.survivalbackend.model;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
public class UserStory {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private Project project;

    private String title;

    private String description;

    @Enumerated
    private UserStoryStatus userStoryStatus;

    @Embedded
    private AdministrativeInformation administrativeInformation;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
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

    public AdministrativeInformation getAdministrativeInformation() {
        return administrativeInformation;
    }

    public void setAdministrativeInformation(AdministrativeInformation administrativeInformation) {
        this.administrativeInformation = administrativeInformation;
    }
}
