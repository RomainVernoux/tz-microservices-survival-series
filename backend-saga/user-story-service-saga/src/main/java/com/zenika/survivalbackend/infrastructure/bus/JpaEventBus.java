package com.zenika.survivalbackend.infrastructure.bus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zenika.survivalbackend.domain.Event;
import com.zenika.survivalbackend.domain.EventBus;
import com.zenika.survivalbackend.domain.EventHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class JpaEventBus implements EventBus {


    private ObjectMapper objectMapper;
    private ActivityRepository activityRepository;
    private final Map<Class<? extends Event>, Set<EventHandler>> subscribers = new HashMap<>();

    public JpaEventBus(ObjectMapper objectMapper,
                       ActivityRepository activityRepository) {
        this.objectMapper = objectMapper;
        this.activityRepository = activityRepository;
    }

    @Override
    public void subscribe(Class<? extends Event> eventClass, EventHandler eventHandler) {
        subscribers.computeIfAbsent(eventClass, key -> new HashSet<>()).add(eventHandler);
    }

    @Override
    public void emit(Event event) {
        try {
            Activity activity = new Activity(UUID.randomUUID(), event.getId(),
                    ActivityDirection.OUTBOUND, objectMapper.writeValueAsString(event));
            activityRepository.save(activity);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void receiveEvent(Event event) {
        if (activityRepository.existsByEventId(event.getId()))
            throw new IllegalArgumentException("Duplicate event detected " + event.getId());

        try {
            Activity activity = new Activity(UUID.randomUUID(), event.getId(),
                    ActivityDirection.INBOUND, objectMapper.writeValueAsString(event));
            activity.setHandledDate(LocalDateTime.now());
            activity.setHandled(true);
            activityRepository.save(activity);

            subscribers.getOrDefault(event.getClass(), Collections.emptySet()).forEach(
                    handler -> handler.handle(event)
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void markAsHandled(Activity activity) {
        activity.setHandled(true);
        activity.setHandledDate(LocalDateTime.now());
        activityRepository.save(activity);
    }

    public List<Activity> getPendingOutboundActivities() {
        return activityRepository.findByActivityDirectionAndHandledFalse(ActivityDirection.OUTBOUND);
    }

}