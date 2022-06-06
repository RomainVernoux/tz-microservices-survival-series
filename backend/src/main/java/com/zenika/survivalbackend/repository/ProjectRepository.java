package com.zenika.survivalbackend.repository;

import com.zenika.survivalbackend.model.Project;
import com.zenika.survivalbackend.model.UserStory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {
}
