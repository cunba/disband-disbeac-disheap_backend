package com.cpilosenlaces.microservice.service.disheap;

import java.util.List;
import java.util.UUID;

import com.cpilosenlaces.microservice.exception.NotFoundException;
import com.cpilosenlaces.microservice.model.disheap.Event;

public interface EventService {
    List<Event> findByStartDateBetweenAndUserId(long minDate, long maxDate, UUID userId);

    List<Event> findByTypeAndUserId(String type, UUID userId);

    List<Event> findByUserId(UUID userId);

    Event findById(UUID id) throws NotFoundException;

    Event save(Event event);

    void delete(Event event);

    void deleteByUserId(List<Event> events);
}
