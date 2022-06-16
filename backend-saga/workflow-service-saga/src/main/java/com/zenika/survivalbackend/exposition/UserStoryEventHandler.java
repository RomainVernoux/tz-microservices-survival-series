package com.zenika.survivalbackend.exposition;

import com.zenika.survivalbackend.domain.UserStoryChangeStatusScheduled;
import com.zenika.survivalbackend.infrastructure.bus.JpaEventBus;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class UserStoryEventHandler {

    JpaEventBus jpaEventBus;

    public UserStoryEventHandler(JpaEventBus jpaEventBus) {
        this.jpaEventBus = jpaEventBus;
    }

    @RabbitListener(queues = "${spring.rabbitmq.workflow-queue}")
    public void receivedMessage(UserStoryChangeStatusScheduled event) {
        jpaEventBus.receiveInboundEvent(event);
    }
}
