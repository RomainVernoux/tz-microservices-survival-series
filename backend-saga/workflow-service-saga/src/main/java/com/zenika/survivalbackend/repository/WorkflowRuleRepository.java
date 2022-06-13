package com.zenika.survivalbackend.repository;

import com.zenika.survivalbackend.model.workflow.UserStoryStatus;
import com.zenika.survivalbackend.model.workflow.WorkflowRule;

import java.util.Optional;
import java.util.UUID;

public interface WorkflowRuleRepository {

    Optional<WorkflowRule> findFirstByProjectIdAndUserStoryStatus(UUID projectId, UserStoryStatus userStoryStatus);
}
