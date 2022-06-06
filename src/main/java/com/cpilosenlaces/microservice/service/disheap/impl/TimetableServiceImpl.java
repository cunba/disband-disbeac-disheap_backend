package com.cpilosenlaces.microservice.service.disheap.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpilosenlaces.microservice.exception.NotFoundException;
import com.cpilosenlaces.microservice.model.disheap.Timetable;
import com.cpilosenlaces.microservice.repository.disheap.TimetableRepository;
import com.cpilosenlaces.microservice.service.disheap.TimetableService;

@Service
public class TimetableServiceImpl implements TimetableService {

    @Autowired
    private TimetableRepository tr;

    @Override
    public List<Timetable> findByUserId(UUID userId) {
        return tr.findByUserIdOrderByWeekDayDesc(userId);
    }

    @Override
    public Timetable findById(UUID id) throws NotFoundException {
        return tr.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public Timetable save(Timetable timetable) {
        return tr.save(timetable);
    }

    @Override
    public void delete(Timetable timetable) {
        tr.delete(timetable);
    }

    @Override
    public void deleteByUserId(List<Timetable> timetables) {
        tr.deleteAll(timetables);
    }
    
}
