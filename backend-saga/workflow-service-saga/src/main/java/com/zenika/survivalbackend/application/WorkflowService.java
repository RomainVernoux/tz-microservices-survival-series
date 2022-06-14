package com.zenika.survivalbackend.application;

import com.zenika.survivalbackend.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class WorkflowService implements EventHandler<UserStoryChangeStatusScheduled> {

    Logger logger = LoggerFactory.getLogger(WorkflowService.class);

    private final WorkflowRuleRepository workflowRuleRepository;
    private final EventBus eventBus;

    public WorkflowService(WorkflowRuleRepository workflowRuleRepository, EventBus eventBus) {
        this.workflowRuleRepository = workflowRuleRepository;
        this.eventBus = eventBus;
    }

    @PostConstruct
    public void subscribeToUserStoriesStatusChanges() {
        this.eventBus.subscribe(UserStoryChangeStatusScheduled.class, this);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void handle(UserStoryChangeStatusScheduled event) {
        List<WorkflowRule> workflowRules = workflowRuleRepository.findAllByProjectId(event.getProjectId());
        workflowRules.forEach(workflowRule -> {
            List<Event> events = workflowRule.userStoryTransitions(
                    event.getUserStoryId(), event.getOldStatus(), event.getNewStatus());
            events.forEach(eventBus::emit);
            workflowRuleRepository.save(workflowRule);
        });
    }

}
