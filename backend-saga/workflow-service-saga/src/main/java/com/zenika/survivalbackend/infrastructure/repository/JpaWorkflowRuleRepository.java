package com.zenika.survivalbackend.infrastructure.repository;

import com.zenika.survivalbackend.domain.userstory.UserStoryStatus;
import com.zenika.survivalbackend.domain.workflow.WorkflowRule;
import com.zenika.survivalbackend.domain.workflow.WorkflowRuleRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class JpaWorkflowRuleRepository implements WorkflowRuleRepository {
    WorkflowRuleDao workflowRuleDao;

    public JpaWorkflowRuleRepository(WorkflowRuleDao workflowRuleDao) {
        this.workflowRuleDao = workflowRuleDao;
    }

    @Override
    public WorkflowRule findFirstByProjectIdAndUserStoryStatus(UUID projectId, UserStoryStatus userStoryStatus) {
        return workflowRuleDao.findFirstByProjectIdAndUserStoryStatus(projectId, userStoryStatus);
    }

    @Override
    public void save(WorkflowRule workflowRule) {
        workflowRuleDao.save(workflowRule);
    }
}
