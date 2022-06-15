package com.zenika.survivalbackend.infrastructure;

import com.zenika.survivalbackend.domain.Event;
import com.zenika.survivalbackend.domain.EventBus;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RabbitEventBus implements EventBus {

    @Value("${spring.rabbitmq.userStory-exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.routingKey}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;

    public RabbitEventBus(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void emit(Event event) {
        rabbitTemplate.convertAndSend(exchange, routingKey, event);
    }
}
