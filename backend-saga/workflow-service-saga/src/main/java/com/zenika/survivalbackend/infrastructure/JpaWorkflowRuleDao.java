package com.zenika.survivalbackend.infrastructure;

import com.zenika.survivalbackend.domain.WorkflowRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaWorkflowRuleDao extends JpaRepository<WorkflowRule, UUID> {
    List<WorkflowRule> findAllByProjectId(UUID projectId);
}
