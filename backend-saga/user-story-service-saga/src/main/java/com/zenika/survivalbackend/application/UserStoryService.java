package com.zenika.survivalbackend.application;

import com.zenika.survivalbackend.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserStoryService {

    private final UserStoryRepository userStoryRepository;
    private final EventBus eventBus;

    public UserStoryService(UserStoryRepository userStoryRepository,
                            EventBus eventBus) {
        this.userStoryRepository = userStoryRepository;
        this.eventBus = eventBus;
    }

    public List<UserStory> getAllUserStories() {
        return userStoryRepository.findAll();
    }


    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void changeUserStoryStatus(UUID userStoryId, UserStoryStatus status) {
        UserStory userStory = userStoryRepository.find(userStoryId);
        List<Event> events = userStory.changeStatus(status);
        userStoryRepository.save(userStory);
        events.forEach(eventBus::emit);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public void validateUserStoryStatus(UUID userStoryId, UserStoryStatus newStatus, boolean isAccepted, Date newStatusDate) {
        UserStory userStory = userStoryRepository.find(userStoryId);
        userStory.confirmUpdateStatus(isAccepted, newStatus, newStatusDate);
        userStoryRepository.save(userStory);

    }
}
