package com.cpilosenlaces.disheap_backend.repository;

import java.util.List;
import java.util.UUID;

import com.cpilosenlaces.disheap_backend.model.Pressure;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PressureRepository extends CrudRepository<Pressure, UUID> {
    List<Pressure> findByDisbandId(UUID disbandId);

    List<Pressure> findByDateBetweenAndDisbandId(long minDate, long maxDate, UUID disbandId);

    List<Pressure> findByDateBetween(long minDate, long maxDate);

    List<Pressure> findAll();
}
