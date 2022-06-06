package com.zenika.survivalbackend.service;

import com.zenika.survivalbackend.model.Project;
import com.zenika.survivalbackend.model.UserStory;
import com.zenika.survivalbackend.model.WorkflowRule;
import com.zenika.survivalbackend.repository.ProjectRepository;
import com.zenika.survivalbackend.repository.UserStoryRepository;
import com.zenika.survivalbackend.repository.WorkflowRuleRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    public List<UserStory> getAllUserStories() {
        return userStoryRepository.findAll();
    }

    @Transactional
    public void editUserStory(UserStory userStory) {
        Project project = projectRepository.getReferenceById(userStory.getProjectId());
        if (project.getApplyWorkflows()) {
            List<WorkflowRule> workflowRules = workflowRuleRepository.findAllByProjectIdAndUserStoryStatus(
                    userStory.getProjectId(), userStory.getUserStoryStatus());
            workflowRules.forEach(workflowRule -> {
                long count = userStoryRepository.countByProjectIdAndUserStoryStatusAndIdNot(userStory.getProjectId(),
                        userStory.getUserStoryStatus(), userStory.getId());
                if (count >= workflowRule.getMaxNumberOfUserStories())
                    throw new IllegalArgumentException("The maximum number of stories in status has been reached");
            });
        }
        userStoryRepository.save(userStory);
    }
}
