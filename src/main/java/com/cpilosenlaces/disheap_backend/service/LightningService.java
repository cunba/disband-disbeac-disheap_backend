package com.cpilosenlaces.disheap_backend.service;

import java.util.List;
import java.util.UUID;

import com.cpilosenlaces.disheap_backend.exception.NotFoundException;
import com.cpilosenlaces.disheap_backend.model.Lightning;

public interface LightningService {
    List<Lightning> findByDisbandId(UUID disbandId);

    List<Lightning> findByDateBetweenAndDisbandId(long minDate, long maxDate, UUID disbandId);

    List<Lightning> findByDateBetween(long minDate, long maxDate);

    Lightning findById(UUID id) throws NotFoundException;

    List<Lightning> findAll();

    Lightning save(Lightning lightning);

    void deleteByDisband(List<Lightning> listLightning);
}
