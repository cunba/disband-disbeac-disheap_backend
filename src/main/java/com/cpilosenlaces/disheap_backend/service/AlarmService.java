package com.cpilosenlaces.disheap_backend.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.cpilosenlaces.disheap_backend.exception.NotFoundException;
import com.cpilosenlaces.disheap_backend.model.Alarm;

public interface AlarmService {
    List<Alarm> findByDisbandId(UUID disbandId);

    List<Alarm> findByDateBetweenAndDisbandId(LocalDateTime minDate, LocalDateTime maxDate, UUID disbandId);

    Alarm findById(UUID id) throws NotFoundException;

    List<Alarm> findAll();

    Alarm save(Alarm alarm);

    void delete(Alarm alarm);

    void deleteAll();
}
