package com.zenika.survivalbackend.controller;

import com.zenika.survivalbackend.infrastructure.repository.UserStoryRepositoryJpa;
import com.zenika.survivalbackend.model.userstory.UserStory;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-stories")
@Profile("test")
public class UserStoryAdministrationController {

    private UserStoryRepositoryJpa userStoryRepository;

    public UserStoryAdministrationController(UserStoryRepositoryJpa userStoryRepository) {
        this.userStoryRepository = userStoryRepository;
    }

    @DeleteMapping
    public void deleteAll() {
        userStoryRepository.deleteAll();
    }

    @PostMapping
    public UserStory createUserStory(@RequestBody UserStory userStory) {
        return userStoryRepository.save(userStory);
    }

}
