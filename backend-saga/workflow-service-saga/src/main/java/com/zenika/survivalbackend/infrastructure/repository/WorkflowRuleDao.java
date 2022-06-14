package com.zenika.survivalbackend.infrastructure.repository;

import com.zenika.survivalbackend.domain.WorkflowRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface WorkflowRuleDao extends JpaRepository<WorkflowRule, UUID> {
    List<WorkflowRule> findAllByProjectId(UUID projectId);
}
