package com.zenika.survivalbackend.service;

import com.zenika.survivalbackend.model.UserStory;
import com.zenika.survivalbackend.model.WorkflowRule;
import com.zenika.survivalbackend.repository.UserStoryRepository;
import com.zenika.survivalbackend.repository.WorkflowRuleRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserStoryService {

    private final UserStoryRepository userStoryRepository;
    private final WorkflowRuleRepository workflowRuleRepository;

    public UserStoryService(UserStoryRepository userStoryRepository, WorkflowRuleRepository workflowRuleRepository) {
        this.userStoryRepository = userStoryRepository;
        this.workflowRuleRepository = workflowRuleRepository;
    }

    public List<UserStory> getAllUserStories() {
        return userStoryRepository.findAll();
    }

    @Transactional
    public void editUserStory(UserStory userStory) {
        Optional<WorkflowRule> workflowRules = workflowRuleRepository.findFirstByProjectIdAndUserStoryStatus(
                userStory.getProjectId(), userStory.getUserStoryStatus());
        workflowRules.ifPresent(workflowRule -> {
            long count = userStoryRepository.countByProjectIdAndUserStoryStatusAndIdNot(userStory.getProjectId(),
                    userStory.getUserStoryStatus(), userStory.getId());
            if (count >= workflowRule.getMaxNumberOfUserStories())
                throw new IllegalArgumentException("The maximum number of stories in status has been reached");
        });
        userStoryRepository.save(userStory);
    }
}
