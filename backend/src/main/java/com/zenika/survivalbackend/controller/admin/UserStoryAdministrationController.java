package com.zenika.survivalbackend.controller.admin;

import com.zenika.survivalbackend.model.UserStory;
import com.zenika.survivalbackend.repository.UserStoryRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-stories")
@Profile("test")
public class UserStoryAdministrationController {

    private UserStoryRepository userStoryRepository;

    public UserStoryAdministrationController(UserStoryRepository userStoryRepository) {
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
