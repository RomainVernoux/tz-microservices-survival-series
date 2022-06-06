package com.zenika.survivalbackend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Workflow {
    @Id
    @GeneratedValue
    private UUID id;
}
