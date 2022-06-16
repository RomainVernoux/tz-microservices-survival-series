package com.zenika.survivalbackend.exposition.admin;

import com.zenika.survivalbackend.domain.WorkflowRule;
import com.zenika.survivalbackend.infrastructure.JpaWorkflowRuleDao;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workflow-rules")
@Profile("test")
public class WorkflowRuleAdministrationController {

    private final JpaWorkflowRuleDao jpaWorkflowRuleDao;

    public WorkflowRuleAdministrationController(JpaWorkflowRuleDao jpaWorkflowRuleDao) {
        this.jpaWorkflowRuleDao = jpaWorkflowRuleDao;
    }

    @DeleteMapping
    public void deleteAll() {
        jpaWorkflowRuleDao.deleteAll();
    }

    @PostMapping
    public WorkflowRule saveWorkflowRule(@RequestBody WorkflowRule workflowRule) {
        return jpaWorkflowRuleDao.save(workflowRule);
    }

}
