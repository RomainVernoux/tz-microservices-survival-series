package com.zenika.survivalbackend.infrastructure.bus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zenika.survivalbackend.domain.Event;
import com.zenika.survivalbackend.domain.EventBus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

@Component
public class TransactionalEventBus implements EventBus {


    private ObjectMapper objectMapper;
    private ActivityRepository activityRepository;

    public TransactionalEventBus(ObjectMapper objectMapper,
                                 ActivityRepository activityRepository) {
        this.objectMapper = objectMapper;
        this.activityRepository = activityRepository;
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
    public <T extends Event> void onEvent(T event, Consumer<T> consumer) {
        if (activityRepository.existsByEventId(event.getId()))
            return;

        try {
            Activity activity = new Activity(UUID.randomUUID(), event.getId(),
                    ActivityDirection.INBOUND, objectMapper.writeValueAsString(event));
            activity.setHandledDate(LocalDateTime.now());
            activity.setHandled(true);
            activityRepository.save(activity);

            consumer.accept(event);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
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
