package com.zenika.survivalbackend.model.userstory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserStoryRepository {
    Optional<UserStory> findById(UUID id);

    List<UserStory> findAll();

    UserStory save(UserStory userStory);
}
