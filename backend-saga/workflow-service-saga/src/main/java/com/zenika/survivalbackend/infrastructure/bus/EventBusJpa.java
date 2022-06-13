package com.zenika.survivalbackend.infrastructure.bus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zenika.survivalbackend.model.Event;
import com.zenika.survivalbackend.model.EventBus;
import com.zenika.survivalbackend.model.EventHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class EventBusJpa implements EventBus {


    private ObjectMapper objectMapper;
    private ActivityRepository activityRepository;
    private final Map<Class<? extends Event>, Set<EventHandler>> subscribers = new HashMap<>();

    public EventBusJpa(ObjectMapper objectMapper,
                       ActivityRepository activityRepository) {
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
                        Activity activity = new Activity(UUID.randomUUID(), event.getId(),
                                ActivityDirection.OUTBOUND, objectMapper.writeValueAsString(event));
                        activityRepository.save(activity);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }

    public List<Activity> getPendingOutboundActivities() {
        return activityRepository.findByActivityDirectionAndHandledFalse(ActivityDirection.OUTBOUND);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void markAsHandled(Activity activity) {
        activity.setHandled(true);
        activity.setHandledDate(LocalDateTime.now());
        activityRepository.save(activity);
    }

}
