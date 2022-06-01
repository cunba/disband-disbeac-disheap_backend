package com.cpilosenlaces.disheap_backend.service;

import java.util.List;
import java.util.UUID;

import com.cpilosenlaces.disheap_backend.exception.NotFoundException;
import com.cpilosenlaces.disheap_backend.model.HeartRate;

public interface HeartRateService {
    List<HeartRate> findByDisbandId(UUID disbandId);

    List<HeartRate> findByDateBetweenAndDisbandId(long minDate, long maxDate, UUID disbandId);

    List<HeartRate> findByDateBetween(long minDate, long maxDate);

    HeartRate findById(UUID id) throws NotFoundException;

    List<HeartRate> findAll();

    HeartRate save(HeartRate heartRate);

    void deleteByDisband(List<HeartRate> listHeartRate);
}
