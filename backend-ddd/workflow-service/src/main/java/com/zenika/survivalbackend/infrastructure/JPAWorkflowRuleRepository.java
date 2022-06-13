package com.zenika.survivalbackend.infrastructure;

import com.zenika.survivalbackend.domain.WorkflowRule;
import com.zenika.survivalbackend.domain.WorkflowRuleRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class JPAWorkflowRuleRepository implements WorkflowRuleRepository {

    private final WorkflowRuleDAO workflowRuleDAO;

    public JPAWorkflowRuleRepository(WorkflowRuleDAO workflowRuleDAO) {
        this.workflowRuleDAO = workflowRuleDAO;
    }

    @Override
    public List<WorkflowRule> findAllByProjectId(UUID projectId) {
        return workflowRuleDAO.findAllByProjectId(projectId);
    }

    @Override
    public void save(WorkflowRule workflowRule) {
        workflowRuleDAO.save(workflowRule);
    }
}
