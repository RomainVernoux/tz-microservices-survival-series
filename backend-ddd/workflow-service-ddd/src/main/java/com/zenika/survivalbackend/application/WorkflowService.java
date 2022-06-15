package com.zenika.survivalbackend.application;

import com.zenika.survivalbackend.domain.RejectedUserStoryTransitionException;
import com.zenika.survivalbackend.domain.UserStoryStatus;
import com.zenika.survivalbackend.domain.WorkflowRule;
import com.zenika.survivalbackend.domain.WorkflowRuleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WorkflowService {

    private static final Logger logger = LoggerFactory.getLogger(WorkflowService.class);

    private final WorkflowRuleRepository workflowRuleRepository;

    public WorkflowService(WorkflowRuleRepository workflowRuleRepository) {
        this.workflowRuleRepository = workflowRuleRepository;
    }

    public void validateUserStoryTransition(UUID projectId, UUID userStoryId, UserStoryStatus oldStatus, UserStoryStatus newStatus) {
        List<WorkflowRule> workflowRules = workflowRuleRepository.findAllByProjectId(projectId);
        try {
            workflowRules.forEach(workflowRule -> {
                workflowRule.userStoryTransitions(oldStatus, newStatus);
                workflowRuleRepository.save(workflowRule);
            });
        } catch (RejectedUserStoryTransitionException e) {
            // FIXME: Oh god.
            logger.error(e.getMessage());
        }
    }
}
