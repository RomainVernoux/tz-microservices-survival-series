package com.zenika.survivalbackend.application;

import com.zenika.survivalbackend.domain.Event;
import com.zenika.survivalbackend.domain.EventBus;
import com.zenika.survivalbackend.domain.EventHandler;
import com.zenika.survivalbackend.domain.userstory.UserStoryChangeStatusScheduled;
import com.zenika.survivalbackend.domain.workflow.WorkflowRule;
import com.zenika.survivalbackend.domain.workflow.WorkflowRuleRepository;
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
