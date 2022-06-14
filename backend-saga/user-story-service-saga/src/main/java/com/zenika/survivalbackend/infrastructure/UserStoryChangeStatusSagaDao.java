package com.zenika.survivalbackend.infrastructure;

import com.zenika.survivalbackend.domain.UserStoryChangeStatusSaga;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserStoryChangeStatusSagaDao extends JpaRepository<UserStoryChangeStatusSaga, UUID> {
    Optional<UserStoryChangeStatusSaga> findFirstByUserStoryIdAndAndWorkflowRuleResponseDateIsNull(UUID userStoryId);
}
