package com.zenika.survivalbackend.domain.workflow;

import com.zenika.survivalbackend.domain.userstory.UserStoryStatus;

import java.util.UUID;

public interface WorkflowRuleRepository {

    WorkflowRule findFirstByProjectIdAndUserStoryStatus(UUID projectId, UserStoryStatus userStoryStatus);

    WorkflowRule save(WorkflowRule workflowRule);
}
