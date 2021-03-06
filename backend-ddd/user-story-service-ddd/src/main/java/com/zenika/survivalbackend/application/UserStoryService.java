package com.zenika.survivalbackend.application;

import com.zenika.survivalbackend.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UserStoryService {

    private final UserStoryRepository userStoryRepository;
    private final EventBus eventBus;

    public UserStoryService(UserStoryRepository userStoryRepository, EventBus eventBus) {
        this.userStoryRepository = userStoryRepository;
        this.eventBus = eventBus;
    }

    public List<UserStory> getAllUserStories() {
        return userStoryRepository.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public void changeUserStoryStatus(UUID id, UserStoryStatus newStatus) {
        UserStory userStory = userStoryRepository.find(id);
        List<Event> events = userStory.changeStatus(newStatus);
        userStoryRepository.save(userStory);
        events.forEach(eventBus::emit);
    }
}
