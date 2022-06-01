package com.cpilosenlaces.disheap_backend.service;

import java.util.List;
import java.util.UUID;

import com.cpilosenlaces.disheap_backend.exception.NotFoundException;
import com.cpilosenlaces.disheap_backend.model.Humidity;

public interface HumidityService {
    List<Humidity> findByDisbandId(UUID disbandId);

    List<Humidity> findByDateBetweenAndDisbandId(long minDate, long maxDate, UUID disbandId);

    List<Humidity> findByDateBetween(long minDate, long maxDate);

    Humidity findById(UUID id) throws NotFoundException;

    List<Humidity> findAll();

    Humidity save(Humidity humidity);

    void deleteByDisband(List<Humidity> listHumidity);
}
