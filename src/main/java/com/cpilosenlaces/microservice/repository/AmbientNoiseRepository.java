package com.cpilosenlaces.microservice.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cpilosenlaces.microservice.model.AmbientNoise;

@Repository
public interface AmbientNoiseRepository extends CrudRepository<AmbientNoise, UUID> {
    List<AmbientNoise> findByDisbandId(UUID disbandId);

    List<AmbientNoise> findByDateBetweenAndDisbandId(long minDate, long maxDate, UUID disbandId);

    List<AmbientNoise> findByDateBetween(long minDate, long maxDate);

    List<AmbientNoise> findAll();
}
