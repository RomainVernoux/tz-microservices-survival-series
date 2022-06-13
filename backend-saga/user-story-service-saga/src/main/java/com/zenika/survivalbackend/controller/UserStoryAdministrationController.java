package com.zenika.survivalbackend.controller;

import com.zenika.survivalbackend.infrastructure.repository.UserStoryRepositoryJpa;
import com.zenika.survivalbackend.model.userstory.UserStory;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-stories")
@Profile("test")
public class UserStoryAdministrationController {

    private UserStoryRepositoryJpa userStoryRepositoryJpa;

    public UserStoryAdministrationController(UserStoryRepositoryJpa userStoryRepositoryJpa) {
        this.userStoryRepositoryJpa = userStoryRepositoryJpa;
    }

    @DeleteMapping
    public void deleteAll() {
        userStoryRepositoryJpa.deleteAll();
    }

    @PostMapping
    public UserStory createUserStory(@RequestBody UserStory userStory) {
        return userStoryRepositoryJpa.save(userStory);
    }

}
