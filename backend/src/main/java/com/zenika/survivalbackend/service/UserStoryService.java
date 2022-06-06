package com.zenika.survivalbackend.service;

import com.zenika.survivalbackend.model.UserStory;
import com.zenika.survivalbackend.repository.UserStoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserStoryService {

    private final UserStoryRepository userStoryRepository;

    public UserStoryService(UserStoryRepository userStoryRepository) {
        this.userStoryRepository = userStoryRepository;
    }

    public List<UserStory> retrieveAllUserStories() {
        return userStoryRepository.findAll();
    }
}
