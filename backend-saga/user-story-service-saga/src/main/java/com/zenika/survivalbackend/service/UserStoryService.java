package com.zenika.survivalbackend.service;

import com.zenika.survivalbackend.model.Event;
import com.zenika.survivalbackend.model.EventBus;
import com.zenika.survivalbackend.model.EventHandler;
import com.zenika.survivalbackend.model.userstory.UserStory;
import com.zenika.survivalbackend.model.userstory.UserStoryRepository;
import com.zenika.survivalbackend.model.userstory.UserStoryStatus;
import com.zenika.survivalbackend.model.workflow.WorkflowRuleProcessedUserStory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.UUID;

@Service
public class UserStoryService implements EventHandler<WorkflowRuleProcessedUserStory> {

    Logger logger = LoggerFactory.getLogger(UserStoryService.class);

    private final UserStoryRepository userStoryRepository;
    private final EventBus eventBus;

    public UserStoryService(UserStoryRepository userStoryRepository, EventBus eventBus) {
        this.userStoryRepository = userStoryRepository;
        this.eventBus = eventBus;
    }

    @PostConstruct
    public void subscribeToUserStoriesStatusChanges() {
        this.eventBus.subscribe(WorkflowRuleProcessedUserStory.class, this);
    }

    public List<UserStory> getAllUserStories() {
        return userStoryRepository.findAll();
    }


    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void changeUserStoryStatus(UUID id, UserStoryStatus status) {
        UserStory userStory = userStoryRepository.findById(id).get();

        List<Event> events = userStory.changeStatus(status);
        eventBus.emitAll(events);

        userStoryRepository.save(userStory);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void handle(WorkflowRuleProcessedUserStory event) {
        UserStory userStory = userStoryRepository.findById(event.getUserStoryId()).get();
        userStory.processWorkflowRuleConstraints(event);
        userStoryRepository.save(userStory);

    }
}
