package com.cpilosenlaces.microservice.service.disheap;

import java.util.List;
import java.util.UUID;

import com.cpilosenlaces.microservice.exception.NotFoundException;
import com.cpilosenlaces.microservice.model.disheap.Timetable;

public interface TimetableService {
    List<Timetable> findByUserId(UUID userId);

    Timetable findById(UUID id) throws NotFoundException;

    Timetable save(Timetable timetable);

    void delete(Timetable timetable);

    void deleteByUserId(List<Timetable> timetables);
}
