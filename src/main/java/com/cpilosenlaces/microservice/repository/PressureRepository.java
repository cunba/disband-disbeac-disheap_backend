package com.cpilosenlaces.microservice.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cpilosenlaces.microservice.model.Pressure;

@Repository
public interface PressureRepository extends CrudRepository<Pressure, UUID> {
    List<Pressure> findByDisbandId(UUID disbandId);

    List<Pressure> findByDateBetweenAndDisbandId(long minDate, long maxDate, UUID disbandId);

    List<Pressure> findByDateBetween(long minDate, long maxDate);

    List<Pressure> findAll();
}
