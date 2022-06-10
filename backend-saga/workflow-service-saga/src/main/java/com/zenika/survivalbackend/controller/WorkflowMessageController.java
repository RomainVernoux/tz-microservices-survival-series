package com.zenika.survivalbackend.controller;

import com.zenika.survivalbackend.model.UserStoryEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class WorkflowMessageController {

    private static final Logger logger = LoggerFactory.getLogger(WorkflowMessageController.class);

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void receivedMessage(UserStoryEvent message) {
        logger.info("Message Received is... " + message);
    }
}
