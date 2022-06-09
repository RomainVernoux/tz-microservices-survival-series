package com.zenika.survivalbackend.repository;

import com.zenika.survivalbackend.model.UserStoryStatus;
import com.zenika.survivalbackend.model.WorkflowRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WorkflowRuleRepository extends JpaRepository<WorkflowRule, UUID> {

    Optional<WorkflowRule> findFirstByProjectIdAndUserStoryStatus(UUID projectId, UserStoryStatus userStoryStatus);
}
