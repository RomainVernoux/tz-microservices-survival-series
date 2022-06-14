package com.zenika.survivalbackend.repository;

import com.zenika.survivalbackend.model.userstory.UserStoryStatus;
import com.zenika.survivalbackend.model.workflow.WorkflowRule;

import java.util.UUID;

public interface WorkflowRuleRepository {

    WorkflowRule findFirstByProjectIdAndUserStoryStatus(UUID projectId, UserStoryStatus userStoryStatus);

    WorkflowRule save(WorkflowRule workflowRule);
}
