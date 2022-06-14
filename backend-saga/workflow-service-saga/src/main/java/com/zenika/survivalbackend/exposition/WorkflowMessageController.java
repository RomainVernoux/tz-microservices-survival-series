package com.zenika.survivalbackend.exposition;

import com.zenika.survivalbackend.domain.UserStoryChangeStatusScheduled;
import com.zenika.survivalbackend.infrastructure.bus.EventBusJpa;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class WorkflowMessageController {

    EventBusJpa eventBusJpa;

    public WorkflowMessageController(EventBusJpa eventBusJpa) {
        this.eventBusJpa = eventBusJpa;
    }

    @RabbitListener(queues = "${spring.rabbitmq.workflow-queue}")
    public void receivedMessage(UserStoryChangeStatusScheduled event) {
        eventBusJpa.receiveEvent(event);
    }
}
