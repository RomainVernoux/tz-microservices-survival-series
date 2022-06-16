package com.zenika.survivalbackend.exposition;

import com.zenika.survivalbackend.application.WorkflowService;
import com.zenika.survivalbackend.infrastructure.bus.TransactionalEventBus;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class UserStoryEventHandler {

    TransactionalEventBus transactionalEventBus;
    WorkflowService workflowService;

    public UserStoryEventHandler(TransactionalEventBus transactionalEventBus, WorkflowService workflowService) {
        this.transactionalEventBus = transactionalEventBus;
        this.workflowService = workflowService;
    }

    @RabbitListener(queues = "${spring.rabbitmq.workflow-queue}")
    public void receivedMessage(UserStoryChangeStatusRequested userStoryChangeStatusRequestedEvent) {
        transactionalEventBus.onEvent(userStoryChangeStatusRequestedEvent, event ->
                workflowService.validateUserStoryTransition(
                        event.getProjectId(),
                        event.getUserStoryId(),
                        event.getOldStatus(),
                        event.getNewStatus()));
    }
}
