package com.cpilosenlaces.disheap_backend.repository;

import java.util.List;
import java.util.UUID;

import com.cpilosenlaces.disheap_backend.model.HeartRate;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeartRateRepository extends CrudRepository<HeartRate, UUID> {
    List<HeartRate> findByDisbandId(UUID disbandId);

    List<HeartRate> findByDateBetweenAndDisbandId(long minDate, long maxDate, UUID disbandId);

    List<HeartRate> findByDateBetween(long minDate, long maxDate);

    List<HeartRate> findAll();
}
