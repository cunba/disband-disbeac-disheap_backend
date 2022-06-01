package com.cpilosenlaces.disheap_backend.service;

import java.util.List;
import java.util.UUID;

import com.cpilosenlaces.disheap_backend.exception.NotFoundException;
import com.cpilosenlaces.disheap_backend.model.Pressure;

public interface PressureService {
    List<Pressure> findByDisbandId(UUID disbandId);

    List<Pressure> findByDateBetweenAndDisbandId(long minDate, long maxDate, UUID disbandId);

    List<Pressure> findByDateBetween(long minDate, long maxDate);

    Pressure findById(UUID id) throws NotFoundException;

    List<Pressure> findAll();

    Pressure save(Pressure pressure);

    void deleteByDisband(List<Pressure> listPressure);
}
