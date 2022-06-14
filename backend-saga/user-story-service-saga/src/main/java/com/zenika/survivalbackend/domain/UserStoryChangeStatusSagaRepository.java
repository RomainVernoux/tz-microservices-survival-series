package com.zenika.survivalbackend.domain;

import java.util.Optional;
import java.util.UUID;

public interface UserStoryChangeStatusSagaRepository {
    Optional<UserStoryChangeStatusSaga> findActiveSagaForUserStory(UUID userStory);

    void save(UserStoryChangeStatusSaga userStoryChangeStatusSaga);
}
