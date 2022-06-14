package com.zenika.survivalbackend.exposition;

import com.zenika.survivalbackend.application.WorkflowService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class UserStoryEventHandler {

    private WorkflowService workflowService;

    public UserStoryEventHandler(WorkflowService workflowService) {
        this.workflowService = workflowService;
    }

    @RabbitListener(queues = "${spring.rabbitmq.workflow-queue}")
    public void receivedMessage(UserStoryStatusChangedEvent event) {
        workflowService.validateUserStoryTransition(
                event.getProjectId(),
                event.getUserStoryId(),
                event.getOldStatus(),
                event.getNewStatus());
    }

}
