package com.zenika.survivalbackend.repository;

import com.zenika.survivalbackend.model.UserStory;
import com.zenika.survivalbackend.model.UserStoryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserStoryRepository extends JpaRepository<UserStory, UUID> {

    long countByProjectIdAndUserStoryStatusAndIdNot(UUID projectId, UserStoryStatus status, UUID userStoryId);


}
