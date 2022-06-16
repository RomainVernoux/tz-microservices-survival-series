package com.zenika.survivalbackend.infrastructure.bus;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class RabbitActivitiesSender {

    TransactionalEventBus transactionalEventBus;
    RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.workflow-exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.routingKey}")
    private String routingKey;

    public RabbitActivitiesSender(TransactionalEventBus transactionalEventBus, RabbitTemplate rabbitTemplate) {
        this.transactionalEventBus = transactionalEventBus;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendPendingActivities() {
        List<Activity> activities = transactionalEventBus.getPendingOutboundActivities();
        activities.forEach(activity -> {
            Message message = MessageBuilder.withBody(activity.getBody().getBytes(StandardCharsets.UTF_8)).build();
            rabbitTemplate.send(exchange, routingKey, message);
            transactionalEventBus.markAsHandled(activity);
        });
    }
}
