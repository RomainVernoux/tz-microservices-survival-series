package com.zenika.survivalbackend.controller;

import com.zenika.survivalbackend.application.UserStoryService;
import com.zenika.survivalbackend.domain.userstory.UserStory;
import com.zenika.survivalbackend.domain.userstory.UserStoryStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @PutMapping("/{id}/change-status/{status}")
    public void changeStatus(@PathVariable UUID id, @PathVariable UserStoryStatus status) {
        userStoryService.changeUserStoryStatus(id, status);
    }

    @GetMapping("/test")
    public void test() {
    }
}
