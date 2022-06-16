package com.zenika.survivalbackend.application;

import com.zenika.survivalbackend.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class WorkflowService {

    private static final Logger logger = LoggerFactory.getLogger(WorkflowService.class);

    private final WorkflowRuleRepository workflowRuleRepository;
    private final EventBus eventBus;

    public WorkflowService(WorkflowRuleRepository workflowRuleRepository, EventBus eventBus) {
        this.workflowRuleRepository = workflowRuleRepository;
        this.eventBus = eventBus;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public void validateUserStoryTransition(UUID projectId, UUID userStoryId, UserStoryStatus oldStatus, UserStoryStatus newStatus) {
        List<WorkflowRule> workflowRules = workflowRuleRepository.findAllByProjectId(projectId);
        workflowRules.forEach(workflowRule -> {
            List<Event> events = workflowRule.userStoryTransitions(
                    userStoryId, oldStatus, newStatus);
            events.forEach(eventBus::emit);
            logger.info("User story transition for us {} is {}", userStoryId, events);
            workflowRuleRepository.save(workflowRule);
        });
    }

}
