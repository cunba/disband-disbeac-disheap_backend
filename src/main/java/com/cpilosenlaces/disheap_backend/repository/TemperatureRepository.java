package com.cpilosenlaces.disheap_backend.repository;

import java.util.List;
import java.util.UUID;

import com.cpilosenlaces.disheap_backend.model.Temperature;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemperatureRepository extends CrudRepository<Temperature, UUID> {
    List<Temperature> findByDisbandId(UUID disbandId);

    List<Temperature> findByDateBetweenAndDisbandId(long minDate, long maxDate, UUID disbandId);

    List<Temperature> findByDateBetween(long minDate, long maxDate);

    List<Temperature> findAll();
}
