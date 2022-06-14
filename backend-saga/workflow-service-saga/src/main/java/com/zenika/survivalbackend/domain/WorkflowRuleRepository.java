package com.zenika.survivalbackend.domain;

import java.util.List;
import java.util.UUID;

public interface WorkflowRuleRepository {

    List<WorkflowRule> findAllByProjectId(UUID projectId);

    void save(WorkflowRule workflowRule);
}
