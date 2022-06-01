package com.cpilosenlaces.disheap_backend.repository;

import java.util.List;
import java.util.UUID;

import com.cpilosenlaces.disheap_backend.model.AmbientNoise;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmbientNoiseRepository extends CrudRepository<AmbientNoise, UUID> {
    List<AmbientNoise> findByDisbandId(UUID disbandId);

    List<AmbientNoise> findByDateBetweenAndDisbandId(long minDate, long maxDate, UUID disbandId);

    List<AmbientNoise> findByDateBetween(long minDate, long maxDate);

    List<AmbientNoise> findAll();
}
