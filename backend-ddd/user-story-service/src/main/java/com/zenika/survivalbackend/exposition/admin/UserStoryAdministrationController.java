package com.zenika.survivalbackend.exposition.admin;

import com.zenika.survivalbackend.domain.UserStory;
import com.zenika.survivalbackend.infrastructure.UserStoryDAO;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-stories")
@Profile("test")
public class UserStoryAdministrationController {

    private UserStoryDAO userStoryRepository;

    public UserStoryAdministrationController(UserStoryDAO userStoryRepository) {
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
