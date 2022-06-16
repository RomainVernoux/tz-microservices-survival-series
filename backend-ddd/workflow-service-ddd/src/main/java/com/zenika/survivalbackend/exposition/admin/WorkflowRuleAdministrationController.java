package com.zenika.survivalbackend.exposition.admin;

import com.zenika.survivalbackend.domain.WorkflowRule;
import com.zenika.survivalbackend.infrastructure.WorkflowRuleDao;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workflow-rules")
@Profile("test")
public class WorkflowRuleAdministrationController {

    private WorkflowRuleDao workflowRuleDao;

    public WorkflowRuleAdministrationController(WorkflowRuleDao workflowRuleDao) {
        this.workflowRuleDao = workflowRuleDao;
    }

    @DeleteMapping
    public void deleteAll() {
        workflowRuleDao.deleteAll();
    }

    @PostMapping
    public WorkflowRule saveWorkflowRule(@RequestBody WorkflowRule workflowRule) {
        return workflowRuleDao.save(workflowRule);
    }

}
