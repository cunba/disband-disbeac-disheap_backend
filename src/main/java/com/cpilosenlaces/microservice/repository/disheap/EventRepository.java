package com.cpilosenlaces.microservice.repository.disheap;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cpilosenlaces.microservice.model.disheap.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {
    List<Event> findByStartDateBetweenAndUserIdOrderByStartDateDesc(long minDate, long maxDate, UUID userId);

    List<Event> findByTypeAndUserIdOrderByStartDateDesc(String type, UUID userId);

    List<Event> findByUserId(UUID userId);
}
