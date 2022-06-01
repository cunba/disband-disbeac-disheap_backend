package com.cpilosenlaces.disheap_backend.repository;

import java.util.List;
import java.util.UUID;

import com.cpilosenlaces.disheap_backend.model.Measure;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasureRepository extends CrudRepository<Measure, UUID> {
    List<Measure> findByDisbandId(UUID disbandId);

    List<Measure> findByDateBetweenAndDisbandId(long minDate, long maxDate, UUID disbandId);

    List<Measure> findAll();
}
