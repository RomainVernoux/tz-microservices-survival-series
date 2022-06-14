package com.zenika.survivalbackend.infrastructure.bus;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class RabbitManager {

    EventBusJpa eventBusJpa;
    RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.userStory-exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.routingKey}")
    private String routingKey;

    public RabbitManager(EventBusJpa eventBusJpa, RabbitTemplate rabbitTemplate) {
        this.eventBusJpa = eventBusJpa;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void handlePendingActivities() {
        List<Activity> activities = eventBusJpa.getPendingOutboundActivities();
        activities.forEach(activity -> {
            Message message = MessageBuilder.withBody(activity.getBody().getBytes(StandardCharsets.UTF_8)).build();
            rabbitTemplate.send(exchange, routingKey, message);
            eventBusJpa.markAsHandled(activity);
        });
    }
}
