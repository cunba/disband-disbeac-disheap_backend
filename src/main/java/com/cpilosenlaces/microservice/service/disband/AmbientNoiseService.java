package com.cpilosenlaces.microservice.service.disband;

import java.util.List;
import java.util.UUID;

import com.cpilosenlaces.microservice.exception.NotFoundException;
import com.cpilosenlaces.microservice.model.disband.AmbientNoise;

public interface AmbientNoiseService {
    List<AmbientNoise> findByDisbandId(UUID disbandId);

    List<AmbientNoise> findByDateBetweenAndDisbandId(long minDate, long maxDate, UUID disbandId);

    List<AmbientNoise> findByDateBetween(long minDate, long maxDate);

    AmbientNoise findById(UUID id) throws NotFoundException;

    List<AmbientNoise> findAll();

    AmbientNoise save(AmbientNoise ambientNoise);

    void deleteByDisband(List<AmbientNoise> listAmbientNoise);
}
