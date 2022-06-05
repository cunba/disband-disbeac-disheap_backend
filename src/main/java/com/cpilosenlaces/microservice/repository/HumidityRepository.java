package com.cpilosenlaces.microservice.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cpilosenlaces.microservice.model.Humidity;

@Repository
public interface HumidityRepository extends CrudRepository<Humidity, UUID> {
    List<Humidity> findByDisbandId(UUID disbandId);

    List<Humidity> findByDateBetweenAndDisbandId(long minDate, long maxDate, UUID disbandId);

    List<Humidity> findByDateBetween(long minDate, long maxDate);

    List<Humidity> findAll();
}
