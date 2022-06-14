package com.zenika.survivalbackend.application;

import com.zenika.survivalbackend.domain.Event;
import com.zenika.survivalbackend.domain.EventBus;
import com.zenika.survivalbackend.domain.EventHandler;
import com.zenika.survivalbackend.domain.userstory.UserStory;
import com.zenika.survivalbackend.domain.userstory.UserStoryRepository;
import com.zenika.survivalbackend.domain.userstory.UserStoryStatus;
import com.zenika.survivalbackend.domain.workflow.WorkflowRuleProcessedUserStory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.UUID;

@Service
public class UserStoryService implements EventHandler<WorkflowRuleProcessedUserStory> {

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
        UserStory userStory = userStoryRepository.find(id);
        List<Event> events = userStory.changeStatus(status);
        userStoryRepository.save(userStory);
        events.forEach(eventBus::emit);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void handle(WorkflowRuleProcessedUserStory event) {
        UserStory userStory = userStoryRepository.find(event.getUserStoryId());
        userStory.processWorkflowRuleConstraints(event);
        userStoryRepository.save(userStory);
    }
}
