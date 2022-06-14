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
    private final UserStoryChangeStatusSagaRepository userStoryChangeStatusSagaRepository;
    private final EventBus eventBus;

    public UserStoryService(UserStoryRepository userStoryRepository,
                            UserStoryChangeStatusSagaRepository userStoryChangeStatusSagaRepository,
                            EventBus eventBus) {
        this.userStoryRepository = userStoryRepository;
        this.userStoryChangeStatusSagaRepository = userStoryChangeStatusSagaRepository;
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
        if (userStoryChangeStatusSagaRepository.findActiveSagaForUserStory(userStoryId).isPresent()) {
            return;
        }

        UserStory userStory = userStoryRepository.find(userStoryId);
        List<Event> events = userStory.changeStatus(status);
        userStoryRepository.save(userStory);

        UserStoryChangeStatusSaga saga = UserStoryChangeStatusSaga.from(userStory, status);
        userStoryChangeStatusSagaRepository.save(saga);

        events.forEach(eventBus::emit);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public void handle(WorkflowRuleProcessedUserStory event) {
        userStoryChangeStatusSagaRepository.findActiveSagaForUserStory(event.getUserStoryId()).ifPresent(
                saga -> {
                    saga.applyWorkflowRuleAck(event);
                    userStoryChangeStatusSagaRepository.save(saga);
                    if (saga.proceedWithChangeStatus()) {
                        UserStory userStory = userStoryRepository.find(event.getUserStoryId());
                        userStory.confirmNewStatus(event.getStatus());
                        userStoryRepository.save(userStory);
                    }
                }
        );
    }
}
