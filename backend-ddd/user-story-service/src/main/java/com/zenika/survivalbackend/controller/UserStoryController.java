package com.zenika.survivalbackend.controller;

import com.zenika.survivalbackend.model.UserStory;
import com.zenika.survivalbackend.service.UserStoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-stories")
public class UserStoryController {

    private UserStoryService userStoryService;

    public UserStoryController(UserStoryService userStoryService) {
        this.userStoryService = userStoryService;
    }

    @GetMapping
    public List<UserStory> getAllUserStories() {
        return userStoryService.getAllUserStories();
    }

    @PutMapping
    public void editUserStory(@RequestBody UserStory userStory) {
        userStoryService.editUserStory(userStory);
    }
}
