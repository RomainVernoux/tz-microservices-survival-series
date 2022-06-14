package com.zenika.survivalbackend.exposition;

import com.zenika.survivalbackend.domain.workflow.WorkflowRuleProcessedUserStory;
import com.zenika.survivalbackend.infrastructure.bus.EventBusJpa;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class UserStoryMessageController {
    EventBusJpa eventBusJpa;

    public UserStoryMessageController(EventBusJpa eventBusJpa) {
        this.eventBusJpa = eventBusJpa;
    }

    @RabbitListener(queues = "${spring.rabbitmq.userStory-queue}")
    public void receivedMessage(WorkflowRuleProcessedUserStory event) {
        eventBusJpa.receiveEvent(event);
    }
}
