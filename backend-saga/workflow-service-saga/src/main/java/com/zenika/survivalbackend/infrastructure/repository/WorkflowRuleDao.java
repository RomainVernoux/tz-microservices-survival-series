package com.zenika.survivalbackend.infrastructure.repository;

import com.zenika.survivalbackend.domain.userstory.UserStoryStatus;
import com.zenika.survivalbackend.domain.workflow.WorkflowRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WorkflowRuleDao extends JpaRepository<WorkflowRule, UUID> {
    WorkflowRule findFirstByProjectIdAndUserStoryStatus(UUID projectId, UserStoryStatus userStoryStatus);
    
}
