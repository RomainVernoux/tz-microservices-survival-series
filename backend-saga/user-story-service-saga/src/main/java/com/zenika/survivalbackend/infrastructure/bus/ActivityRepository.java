package com.zenika.survivalbackend.infrastructure.bus;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ActivityRepository extends JpaRepository<Activity, UUID> {
    List<Activity> findByActivityDirectionAndHandledFalse(ActivityDirection activityDirection);

    Boolean existsByEventId(UUID eventId);
}
