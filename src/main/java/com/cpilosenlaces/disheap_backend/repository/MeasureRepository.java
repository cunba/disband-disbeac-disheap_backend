package com.cpilosenlaces.disheap_backend.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.cpilosenlaces.disheap_backend.model.Measure;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasureRepository extends CrudRepository<Measure, UUID> {
    List<Measure> findByDisbandId(UUID disbandId);

    List<Measure> findByDateBetweenAndDisbandId(LocalDateTime minDate, LocalDateTime maxDate, UUID disbandId);

    List<Measure> findAll();
}
