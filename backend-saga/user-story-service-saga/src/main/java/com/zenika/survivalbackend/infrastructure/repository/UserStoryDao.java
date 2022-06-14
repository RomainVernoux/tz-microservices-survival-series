package com.zenika.survivalbackend.infrastructure.repository;

import com.zenika.survivalbackend.domain.userstory.UserStory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserStoryDao extends JpaRepository<UserStory, UUID> {
}
