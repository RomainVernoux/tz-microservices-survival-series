package com.zenika.survivalbackend.controller;

import com.zenika.survivalbackend.repository.UserStoryRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/user-stories")
@Profile("test")
public class UserStoryAdminitrationController {

    private UserStoryRepository userStoryRepository;

    public UserStoryAdminitrationController(UserStoryRepository userStoryRepository) {
        this.userStoryRepository = userStoryRepository;
    }

    @DeleteMapping
    public void deleteAll() {
        userStoryRepository.deleteAll();
    }

}
