package com.zenika.survivalbackend.service;

import com.zenika.survivalbackend.model.Event;
import com.zenika.survivalbackend.model.EventBus;
import com.zenika.survivalbackend.model.EventHandler;
import com.zenika.survivalbackend.model.userstory.UserStoryChangeStatusScheduled;
import com.zenika.survivalbackend.model.workflow.WorkflowRule;
import com.zenika.survivalbackend.repository.WorkflowRuleRepository;
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
        WorkflowRule removeWorkflowRule =
                workflowRuleRepository.findFirstByProjectIdAndUserStoryStatus(event.getProjectId(), event.getPreviousStatus());
        WorkflowRule newWorkflowRule =
                workflowRuleRepository.findFirstByProjectIdAndUserStoryStatus(event.getProjectId(), event.getNewStatus());

        List<Event> events = newWorkflowRule.acceptUserStory(event.getUserStoryId(), removeWorkflowRule);
        eventBus.emitAll(events);

        workflowRuleRepository.save(newWorkflowRule);
        workflowRuleRepository.save(removeWorkflowRule);
    }

}
