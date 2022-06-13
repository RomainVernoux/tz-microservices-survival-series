package com.zenika.survivalbackend.controller.admin;

import com.zenika.survivalbackend.model.WorkflowRule;
import com.zenika.survivalbackend.repository.WorkflowRuleRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workflow-rules")
@Profile("test")
public class WorkflowRuleAdministrationController {

    private WorkflowRuleRepository workflowRuleRepository;

    public WorkflowRuleAdministrationController(WorkflowRuleRepository workflowRuleRepository) {
        this.workflowRuleRepository = workflowRuleRepository;
    }

    @DeleteMapping
    public void deleteAll() {
        workflowRuleRepository.deleteAll();
    }

    @PostMapping
    public WorkflowRule saveWorkflowRule(@RequestBody WorkflowRule workflowRule) {
        return workflowRuleRepository.save(workflowRule);
    }

}
