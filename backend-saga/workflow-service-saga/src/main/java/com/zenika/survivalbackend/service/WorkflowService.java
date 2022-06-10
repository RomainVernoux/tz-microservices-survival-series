package com.zenika.survivalbackend.service;

import com.zenika.survivalbackend.repository.WorkflowRuleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Service
public class WorkflowService {

    Logger logger = LoggerFactory.getLogger(WorkflowService.class);

    private final WorkflowRuleRepository workflowRuleRepository;

    public WorkflowService(WorkflowRuleRepository workflowRuleRepository) {
        this.workflowRuleRepository = workflowRuleRepository;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Retryable(value = SQLException.class, maxAttempts = 5, backoff = @Backoff(delay = 10))
    public void editUserStory() {


    }
}
