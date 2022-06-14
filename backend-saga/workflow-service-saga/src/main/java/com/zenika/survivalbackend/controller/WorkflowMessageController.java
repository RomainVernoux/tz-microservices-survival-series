package com.zenika.survivalbackend.controller;

import com.zenika.survivalbackend.domain.userstory.UserStoryChangeStatusScheduled;
import com.zenika.survivalbackend.infrastructure.bus.EventBusJpa;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class WorkflowMessageController {

    private static final Logger logger = LoggerFactory.getLogger(WorkflowMessageController.class);

    EventBusJpa eventBusJpa;

    public WorkflowMessageController(EventBusJpa eventBusJpa) {
        this.eventBusJpa = eventBusJpa;
    }

    @RabbitListener(queues = "${spring.rabbitmq.workflow-queue}")
    public void receivedMessage(UserStoryChangeStatusScheduled event) {
        eventBusJpa.receiveEvent(event);
    }
}
