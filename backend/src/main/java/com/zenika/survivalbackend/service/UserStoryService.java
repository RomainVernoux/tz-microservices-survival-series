package com.zenika.survivalbackend.service;

import com.zenika.survivalbackend.model.UserStory;
import com.zenika.survivalbackend.model.WorkflowRule;
import com.zenika.survivalbackend.repository.UserStoryRepository;
import com.zenika.survivalbackend.repository.WorkflowRuleRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public void editUserStory(UserStory userStoryDto) {
        UserStory userStory = userStoryRepository.getReferenceById(userStoryDto.getId());
        BeanUtils.copyProperties(userStoryDto, userStory, "version");

        Optional<WorkflowRule> workflowRules = workflowRuleRepository.findFirstByProjectIdAndUserStoryStatus(
                userStory.getProjectId(), userStory.getUserStoryStatus());
        workflowRules.ifPresent(workflowRule -> {
            long count = userStoryRepository.countByProjectIdAndUserStoryStatusAndIdNot(userStory.getProjectId(),
                    userStory.getUserStoryStatus(), userStory.getId());
            workflowRule.setLock(UUID.randomUUID());
            //workflowRuleRepository.save(workflowRule);
            if (count >= workflowRule.getMaxNumberOfUserStories())
                throw new IllegalArgumentException("The maximum number of stories in status has been reached");
        });

        userStoryRepository.save(userStory);
    }
}
