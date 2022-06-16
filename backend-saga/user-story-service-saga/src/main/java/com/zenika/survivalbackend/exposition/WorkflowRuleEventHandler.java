package com.zenika.survivalbackend.exposition;

import com.zenika.survivalbackend.application.UserStoryService;
import com.zenika.survivalbackend.infrastructure.bus.TransactionalEventBus;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class WorkflowRuleEventHandler {
    TransactionalEventBus transactionalEventBus;
    UserStoryService userStoryService;

    public WorkflowRuleEventHandler(TransactionalEventBus transactionalEventBus, UserStoryService userStoryService) {
        this.transactionalEventBus = transactionalEventBus;
        this.userStoryService = userStoryService;
    }

    @RabbitListener(queues = "${spring.rabbitmq.userStory-queue}")
    public void receivedMessage(WorkflowRuleProcessedUserStory workflowRuleProcessedUserStoryEvent) throws InterruptedException {
        transactionalEventBus.onEvent(workflowRuleProcessedUserStoryEvent, event ->
                userStoryService.validateUserStoryStatus(
                        event.getUserStoryId(),
                        event.isAccepted(),
                        event.getOccurredOn()
                ));
    }
}
