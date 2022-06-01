package com.cpilosenlaces.disheap_backend.service;

import java.util.List;
import java.util.UUID;

import com.cpilosenlaces.disheap_backend.exception.NotFoundException;
import com.cpilosenlaces.disheap_backend.model.Oxygen;

public interface OxygenService {
    List<Oxygen> findByDisbandId(UUID disbandId);

    List<Oxygen> findByDateBetweenAndDisbandId(long minDate, long maxDate, UUID disbandId);

    List<Oxygen> findByDateBetween(long minDate, long maxDate);

    Oxygen findById(UUID id) throws NotFoundException;

    List<Oxygen> findAll();

    Oxygen save(Oxygen oxygen);

    void deleteByDisband(List<Oxygen> listOxygen);
}
