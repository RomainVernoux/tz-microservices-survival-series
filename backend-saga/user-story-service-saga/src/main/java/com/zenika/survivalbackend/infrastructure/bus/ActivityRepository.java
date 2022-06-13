package com.zenika.survivalbackend.infrastructure.bus;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ActivityRepository extends CrudRepository<Activity, UUID> {
}
