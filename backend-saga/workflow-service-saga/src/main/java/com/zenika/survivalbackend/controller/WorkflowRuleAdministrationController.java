package com.zenika.survivalbackend.controller;

import com.zenika.survivalbackend.infrastructure.repository.WorkflowRuleJpaRepository;
import com.zenika.survivalbackend.model.workflow.WorkflowRule;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workflow-rules")
@Profile("test")
public class WorkflowRuleAdministrationController {

    private WorkflowRuleJpaRepository workflowRuleJpaRepository;

    public WorkflowRuleAdministrationController(WorkflowRuleJpaRepository workflowRuleJpaRepository) {
        this.workflowRuleJpaRepository = workflowRuleJpaRepository;
    }

    @DeleteMapping
    public void deleteAll() {
        workflowRuleJpaRepository.deleteAll();
    }

    @PostMapping
    public WorkflowRule saveWorkflowRule(@RequestBody WorkflowRule workflowRule) {
        return workflowRuleJpaRepository.save(workflowRule);
    }

}
