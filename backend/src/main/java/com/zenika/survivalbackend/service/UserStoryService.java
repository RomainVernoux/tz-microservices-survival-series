package com.zenika.survivalbackend.service;

import com.zenika.survivalbackend.model.UserStory;
import com.zenika.survivalbackend.model.WorkflowRule;
import com.zenika.survivalbackend.repository.UserStoryRepository;
import com.zenika.survivalbackend.repository.WorkflowRuleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class UserStoryService {

    Logger logger = LoggerFactory.getLogger(UserStoryService.class);

    private final UserStoryRepository userStoryRepository;
    private final WorkflowRuleRepository workflowRuleRepository;

    public UserStoryService(UserStoryRepository userStoryRepository, WorkflowRuleRepository workflowRuleRepository) {
        this.userStoryRepository = userStoryRepository;
        this.workflowRuleRepository = workflowRuleRepository;
    }

    public List<UserStory> getAllUserStories() {
        return userStoryRepository.findAll();
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Retryable(value = SQLException.class, maxAttempts = 5)
    public void editUserStory(UserStory userStory) {

        Optional<WorkflowRule> workflowRules = workflowRuleRepository.findFirstByProjectIdAndUserStoryStatus(
                userStory.getProjectId(), userStory.getUserStoryStatus());
        workflowRules.ifPresent(workflowRule -> {
            int currentCount = workflowRule.getCurrentNumberOfUserStories();
            logger.info("Current count {}", currentCount);

            if (currentCount >= workflowRule.getMaxNumberOfUserStories())
                throw new IllegalArgumentException("The maximum number of stories in status has been reached");

            workflowRule.setCurrentNumberOfUserStories(currentCount + 1);
        });

        userStoryRepository.save(userStory);
    }
}
