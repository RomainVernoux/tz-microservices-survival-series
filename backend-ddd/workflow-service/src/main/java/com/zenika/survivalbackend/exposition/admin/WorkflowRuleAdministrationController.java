package com.zenika.survivalbackend.exposition.admin;

import com.zenika.survivalbackend.domain.WorkflowRule;
import com.zenika.survivalbackend.infrastructure.WorkflowRuleDAO;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workflow-rules")
@Profile("test")
public class WorkflowRuleAdministrationController {

    private WorkflowRuleDAO workflowRuleRepository;

    public WorkflowRuleAdministrationController(WorkflowRuleDAO workflowRuleRepository) {
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
