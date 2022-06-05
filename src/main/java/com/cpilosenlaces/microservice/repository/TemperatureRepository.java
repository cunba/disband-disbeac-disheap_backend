package com.cpilosenlaces.microservice.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cpilosenlaces.microservice.model.Temperature;

@Repository
public interface TemperatureRepository extends CrudRepository<Temperature, UUID> {
    List<Temperature> findByDisbandId(UUID disbandId);

    List<Temperature> findByDateBetweenAndDisbandId(long minDate, long maxDate, UUID disbandId);

    List<Temperature> findByDateBetween(long minDate, long maxDate);

    List<Temperature> findAll();
}
