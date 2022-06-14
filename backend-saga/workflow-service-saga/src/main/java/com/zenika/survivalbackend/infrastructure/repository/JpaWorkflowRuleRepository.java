package com.zenika.survivalbackend.infrastructure.repository;

import com.zenika.survivalbackend.domain.WorkflowRule;
import com.zenika.survivalbackend.domain.WorkflowRuleRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class JpaWorkflowRuleRepository implements WorkflowRuleRepository {
    WorkflowRuleDao workflowRuleDao;

    public JpaWorkflowRuleRepository(WorkflowRuleDao workflowRuleDao) {
        this.workflowRuleDao = workflowRuleDao;
    }

    @Override
    public List<WorkflowRule> findAllByProjectId(UUID projectId) {
        return workflowRuleDao.findAllByProjectId(projectId);
    }

    @Override
    public void save(WorkflowRule workflowRule) {
        workflowRuleDao.save(workflowRule);
    }
}
