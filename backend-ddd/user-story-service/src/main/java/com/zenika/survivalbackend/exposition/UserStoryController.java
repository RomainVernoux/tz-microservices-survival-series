package com.zenika.survivalbackend.exposition;

import com.zenika.survivalbackend.application.UserStoryService;
import com.zenika.survivalbackend.domain.UserStory;
import com.zenika.survivalbackend.domain.UserStoryStatus;
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

    @PostMapping("/{id}/change-status")
    public void changeUserStoryStatus(@PathVariable UUID id, @RequestBody ChangeUserStoryStatusDTO dto) {
        userStoryService.changeUserStoryStatus(id, UserStoryStatus.valueOf(dto.newStatus()));
    }
}
