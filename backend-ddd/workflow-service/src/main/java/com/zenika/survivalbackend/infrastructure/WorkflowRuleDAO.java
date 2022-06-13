package com.zenika.survivalbackend.infrastructure;

import com.zenika.survivalbackend.domain.WorkflowRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WorkflowRuleDAO extends JpaRepository<WorkflowRule, UUID> {

    List<WorkflowRule> findAllByProjectId(UUID projectId);
}
