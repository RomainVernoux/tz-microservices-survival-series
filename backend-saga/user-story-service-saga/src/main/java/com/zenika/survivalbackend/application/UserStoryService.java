package com.zenika.survivalbackend.application;

import com.zenika.survivalbackend.domain.*;
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

    public UserStoryService(UserStoryRepository userStoryRepository,
                            EventBus eventBus) {
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
    public void changeUserStoryStatus(UUID userStoryId, UserStoryStatus status) {

        UserStory userStory = userStoryRepository.find(userStoryId);
        if (userStory.isUpdatingStatus())
            throw new IllegalStateException("User story is already being updated");

        List<Event> events = userStory.changeStatus(status);
        userStoryRepository.save(userStory);

        events.forEach(eventBus::emit);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public void handle(WorkflowRuleProcessedUserStory event) {
        UserStory userStory = userStoryRepository.find(event.getUserStoryId());
        userStory.confirmUpdateStatus(event.isAccepted(), event.getStatus(), event.getOccurredOn());
        userStoryRepository.save(userStory);

    }
}
