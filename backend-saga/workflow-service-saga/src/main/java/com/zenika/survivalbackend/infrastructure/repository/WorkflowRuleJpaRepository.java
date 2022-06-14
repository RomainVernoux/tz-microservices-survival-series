package com.zenika.survivalbackend.infrastructure.repository;

import com.zenika.survivalbackend.model.userstory.UserStoryStatus;
import com.zenika.survivalbackend.model.workflow.WorkflowRule;
import com.zenika.survivalbackend.repository.WorkflowRuleRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WorkflowRuleJpaRepository extends JpaRepository<WorkflowRule, UUID>, WorkflowRuleRepository {
    WorkflowRule findFirstByProjectIdAndUserStoryStatus(UUID projectId, UserStoryStatus userStoryStatus);

    WorkflowRule save(WorkflowRule workflowRule);
}
