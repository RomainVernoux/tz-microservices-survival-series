package com.zenika.survivalbackend.exposition.admin;

import com.zenika.survivalbackend.domain.UserStory;
import com.zenika.survivalbackend.infrastructure.UserStoryDao;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-stories")
@Profile("test")
public class UserStoryAdministrationController {

    private UserStoryDao userStoryDao;

    public UserStoryAdministrationController(UserStoryDao userStoryDao) {
        this.userStoryDao = userStoryDao;
    }

    @DeleteMapping
    public void deleteAll() {
        userStoryDao.deleteAll();
    }

    @PostMapping
    public UserStory createUserStory(@RequestBody UserStory userStory) {
        return userStoryDao.save(userStory);
    }

}
