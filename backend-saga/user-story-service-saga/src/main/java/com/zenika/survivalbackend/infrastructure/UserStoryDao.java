package com.zenika.survivalbackend.infrastructure;

import com.zenika.survivalbackend.domain.UserStory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserStoryDao extends JpaRepository<UserStory, UUID> {
}
