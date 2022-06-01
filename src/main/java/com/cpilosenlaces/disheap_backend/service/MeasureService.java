package com.cpilosenlaces.disheap_backend.service;

import java.util.List;
import java.util.UUID;

import com.cpilosenlaces.disheap_backend.exception.NotFoundException;
import com.cpilosenlaces.disheap_backend.model.Measure;

public interface MeasureService {
    List<Measure> findByDisbandId(UUID disbandId);

    List<Measure> findByDateBetweenAndDisbandId(long minDate, long maxDate, UUID disbandId);

    Measure findById(UUID id) throws NotFoundException;

    List<Measure> findAll();

    Measure save(Measure measure);

    void delete(Measure measure);

    void deleteAll();
}
