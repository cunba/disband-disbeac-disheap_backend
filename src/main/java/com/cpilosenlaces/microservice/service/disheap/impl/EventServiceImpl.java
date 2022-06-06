package com.cpilosenlaces.microservice.service.disheap.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpilosenlaces.microservice.exception.NotFoundException;
import com.cpilosenlaces.microservice.model.disheap.Event;
import com.cpilosenlaces.microservice.repository.disheap.EventRepository;
import com.cpilosenlaces.microservice.service.disheap.EventService;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository er;

    @Override
    public List<Event> findByStartDateBetweenAndUserId(long minDate, long maxDate, UUID userId) {
        return er.findByStartDateBetweenAndUserIdOrderByStartDateDesc(minDate, maxDate, userId);
    }

    @Override
    public List<Event> findByTypeAndUserId(String type, UUID userId) {
        return er.findByTypeAndUserIdOrderByStartDateDesc(type, userId);
    }
    
    @Override
    public List<Event> findByUserId(UUID userId) {
        return er.findByUserId(userId);
    }

    @Override
    public Event findById(UUID id) throws NotFoundException {
        return er.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public Event save(Event event) {
        return er.save(event);
    }

    @Override
    public void delete(Event event) {
        er.delete(event);
    }

    @Override
    public void deleteByUserId(List<Event> events) {
        er.deleteAll(events);
    }
    
}
