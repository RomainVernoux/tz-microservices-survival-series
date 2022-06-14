package com.zenika.survivalbackend.exposition;

import com.zenika.survivalbackend.domain.WorkflowRuleProcessedUserStory;
import com.zenika.survivalbackend.infrastructure.bus.JpaEventBus;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class UserStoryMessageController {
    JpaEventBus jpaEventBus;

    public UserStoryMessageController(JpaEventBus jpaEventBus) {
        this.jpaEventBus = jpaEventBus;
    }

    @RabbitListener(queues = "${spring.rabbitmq.userStory-queue}")
    public void receivedMessage(WorkflowRuleProcessedUserStory event) {
        jpaEventBus.receiveEvent(event);
    }
}
