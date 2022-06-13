package com.zenika.survivalbackend.service;

import com.zenika.survivalbackend.model.Event;
import com.zenika.survivalbackend.model.EventBus;
import com.zenika.survivalbackend.model.userstory.UserStory;
import com.zenika.survivalbackend.model.userstory.UserStoryRepository;
import com.zenika.survivalbackend.model.userstory.UserStoryStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserStoryService {

    Logger logger = LoggerFactory.getLogger(UserStoryService.class);

    private final UserStoryRepository userStoryRepository;
    private final EventBus eventBus;

    public UserStoryService(UserStoryRepository userStoryRepository, EventBus eventBus) {
        this.userStoryRepository = userStoryRepository;
        this.eventBus = eventBus;
    }

    public List<UserStory> getAllUserStories() {
        return userStoryRepository.findAll();
    }


    public void changeUserStoryStatus(UUID id, UserStoryStatus status) {
        UserStory userStory = userStoryRepository.findById(id).get();
        List<Event> events = userStory.changeStatus(status);
        eventBus.emitAll(events);
    }
}
