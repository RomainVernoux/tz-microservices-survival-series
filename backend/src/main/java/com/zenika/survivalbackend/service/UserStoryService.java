package com.zenika.survivalbackend.service;

import com.zenika.survivalbackend.model.Project;
import com.zenika.survivalbackend.model.UserStory;
import com.zenika.survivalbackend.model.WorkflowRule;
import com.zenika.survivalbackend.repository.ProjectRepository;
import com.zenika.survivalbackend.repository.UserStoryRepository;
import com.zenika.survivalbackend.repository.WorkflowRuleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserStoryService {

    private final UserStoryRepository userStoryRepository;
    private final ProjectRepository projectRepository;
    private final WorkflowRuleRepository workflowRuleRepository;

    public UserStoryService(UserStoryRepository userStoryRepository, ProjectRepository projectRepository, WorkflowRuleRepository workflowRuleRepository) {
        this.userStoryRepository = userStoryRepository;
        this.projectRepository = projectRepository;
        this.workflowRuleRepository = workflowRuleRepository;
    }

    public List<UserStory> retrieveAllUserStories() {
        return userStoryRepository.findAll();
    }

    public void saveUserStory(UserStory userStory) {
        Project project = projectRepository.getReferenceById(userStory.getProjectId());
        WorkflowRule workflowRule = workflowRuleRepository.findFirstByProjectIdAndUserStoryStatus(userStory.getProjectId(), userStory.getUserStoryStatus());

        if (project.getApplyWorkflows() && workflowRule != null) {
            long count = userStoryRepository.countByProjectIdAndIdNot(userStory.getProjectId(), userStory.getId());
            if (count >= workflowRule.getMaxNumberOfUserStories())
                throw new IllegalArgumentException("The maximum number of stories in status has been reached");
        }
        userStoryRepository.save(userStory);
    }
}
