package com.zenika.survivalbackend.infrastructure.repository;

import com.zenika.survivalbackend.model.workflow.UserStoryStatus;
import com.zenika.survivalbackend.model.workflow.WorkflowRule;
import com.zenika.survivalbackend.repository.WorkflowRuleRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface WorkflowRuleJpaRepository extends JpaRepository<WorkflowRule, UUID>, WorkflowRuleRepository {
    Optional<WorkflowRule> findFirstByProjectIdAndUserStoryStatus(UUID projectId, UserStoryStatus userStoryStatus);
}
