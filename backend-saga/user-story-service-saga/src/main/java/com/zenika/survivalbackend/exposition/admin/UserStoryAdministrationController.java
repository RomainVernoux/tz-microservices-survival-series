package com.zenika.survivalbackend.exposition.admin;

import com.zenika.survivalbackend.domain.UserStory;
import com.zenika.survivalbackend.infrastructure.JpaUserStoryDao;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-stories")
@Profile("test")
public class UserStoryAdministrationController {

    private JpaUserStoryDao jpaUserStoryDao;

    public UserStoryAdministrationController(JpaUserStoryDao jpaUserStoryDao) {
        this.jpaUserStoryDao = jpaUserStoryDao;
    }

    @DeleteMapping
    public void deleteAll() {
        jpaUserStoryDao.deleteAll();
    }

    @PostMapping
    public UserStory createUserStory(@RequestBody UserStory userStory) {
        return jpaUserStoryDao.save(userStory);
    }

}
