package com.zenika.survivalbackend.infrastructure.repository;

import com.zenika.survivalbackend.model.userstory.UserStory;
import com.zenika.survivalbackend.model.userstory.UserStoryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserStoryRepositoryJpa extends JpaRepository<UserStory, UUID>, UserStoryRepository {
    @Override
    UserStory save(UserStory userStory);
}
