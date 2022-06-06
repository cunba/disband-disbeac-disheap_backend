package com.cpilosenlaces.microservice.service.disband;

import java.util.List;
import java.util.UUID;

import com.cpilosenlaces.microservice.exception.NotFoundException;
import com.cpilosenlaces.microservice.model.disband.Lightning;

public interface LightningService {
    List<Lightning> findByDisbandId(UUID disbandId);

    List<Lightning> findByDateBetweenAndDisbandId(long minDate, long maxDate, UUID disbandId);

    List<Lightning> findByDateBetween(long minDate, long maxDate);

    Lightning findById(UUID id) throws NotFoundException;

    List<Lightning> findAll();

    Lightning save(Lightning lightning);

    void deleteByDisband(List<Lightning> listLightning);
}
