package com.zenika.survivalbackend.infrastructure;

import com.zenika.survivalbackend.domain.WorkflowRule;
import com.zenika.survivalbackend.domain.WorkflowRuleRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class JpaWorkflowRuleRepository implements WorkflowRuleRepository {

    private final JpaWorkflowRuleDao jpaWorkflowRuleDao;

    public JpaWorkflowRuleRepository(JpaWorkflowRuleDao jpaWorkflowRuleDao) {
        this.jpaWorkflowRuleDao = jpaWorkflowRuleDao;
    }

    @Override
    public List<WorkflowRule> findAllByProjectId(UUID projectId) {
        return jpaWorkflowRuleDao.findAllByProjectId(projectId);
    }

    @Override
    public void save(WorkflowRule workflowRule) {
        jpaWorkflowRuleDao.save(workflowRule);
    }
}
