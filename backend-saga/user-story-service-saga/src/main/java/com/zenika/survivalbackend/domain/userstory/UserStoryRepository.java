package com.zenika.survivalbackend.domain.userstory;

import java.util.List;
import java.util.UUID;

public interface UserStoryRepository {
    List<UserStory> findAll();

    UserStory find(UUID id);

    void save(UserStory userStory);
}
