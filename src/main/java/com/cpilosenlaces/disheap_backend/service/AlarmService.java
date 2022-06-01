package com.cpilosenlaces.disheap_backend.service;

import java.util.List;
import java.util.UUID;

import com.cpilosenlaces.disheap_backend.exception.NotFoundException;
import com.cpilosenlaces.disheap_backend.model.Alarm;

public interface AlarmService {
    List<Alarm> findByDisbandId(UUID disbandId);

    List<Alarm> findByDateBetweenAndDisbandId(long minDate, long maxDate, UUID disbandId);

    Alarm findById(UUID id) throws NotFoundException;

    List<Alarm> findAll();

    Alarm save(Alarm alarm);

    void delete(Alarm alarms);

    void deleteByDisband(List<Alarm> alarms);

}
