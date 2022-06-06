package com.zenika.survivalbackend.model;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Project {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    @Embedded
    private AdministrativeInformation administrativeInformation;

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AdministrativeInformation getAdministrativeInformation() {
        return administrativeInformation;
    }

    public void setAdministrativeInformation(AdministrativeInformation administrativeInformation) {
        this.administrativeInformation = administrativeInformation;
    }
}
