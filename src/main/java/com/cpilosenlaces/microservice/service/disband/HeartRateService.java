package com.cpilosenlaces.microservice.service.disband;

import java.util.List;
import java.util.UUID;

import com.cpilosenlaces.microservice.exception.NotFoundException;
import com.cpilosenlaces.microservice.model.disband.HeartRate;

public interface HeartRateService {
    HeartRate findLast1ByDateBetweenAndDisbandId(long minDate, long maxDate, UUID disbandId);

    List<HeartRate> findByDisbandId(UUID disbandId);

    List<HeartRate> findByDateBetweenAndDisbandId(long minDate, long maxDate, UUID disbandId);

    List<HeartRate> findByDateBetween(long minDate, long maxDate);

    HeartRate findById(UUID id) throws NotFoundException;

    List<HeartRate> findAll();

    HeartRate save(HeartRate heartRate);

    void deleteByDisband(List<HeartRate> listHeartRate);
}
