package com.zenika.survivalbackend.infrastructure.bus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zenika.survivalbackend.model.Event;
import com.zenika.survivalbackend.model.EventBus;
import com.zenika.survivalbackend.model.EventHandler;
import com.zenika.survivalbackend.model.userstory.UserStoryChangeStatusScheduled;
import com.zenika.survivalbackend.model.userstory.UserStoryStatus;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RabbitEventBus implements EventBus {

    @Value("${spring.rabbitmq.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.routingKey}")
    private String routingKey;
    private RabbitTemplate rabbitTemplate;
    private ObjectMapper objectMapper;
    private ActivityRepository activityRepository;
    private final Map<Class<? extends Event>, Set<EventHandler>> subscribers = new HashMap<>();

    public RabbitEventBus(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper,
                          ActivityRepository activityRepository) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
        this.activityRepository = activityRepository;
    }

    @Override
    public void subscribe(Class<? extends Event> eventClass, EventHandler eventHandler) {
        subscribers.computeIfAbsent(eventClass, key -> new HashSet<>()).add(eventHandler);
    }

    @Override
    public void emitAll(List<Event> events) {
        events.stream().forEach(
                event -> {
                    try {
                        Activity activity = new Activity(UUID.randomUUID(), ActivityDirection.OUTBOUND, objectMapper.writeValueAsString(event));
                        activityRepository.save(activity);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }

    private void doEmit() {
        rabbitTemplate.convertAndSend(exchange, routingKey, new UserStoryChangeStatusScheduled(UUID.randomUUID(), UserStoryStatus.IN_PROGRESS));
    }
}
