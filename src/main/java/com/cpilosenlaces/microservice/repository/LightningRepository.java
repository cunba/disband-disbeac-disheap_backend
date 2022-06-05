package com.cpilosenlaces.microservice.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cpilosenlaces.microservice.model.Lightning;

@Repository
public interface LightningRepository extends CrudRepository<Lightning, UUID> {
    List<Lightning> findByDisbandId(UUID disbandId);

    List<Lightning> findByDateBetweenAndDisbandId(long minDate, long maxDate, UUID disbandId);

    List<Lightning> findByDateBetween(long minDate, long maxDate);

    List<Lightning> findAll();
}
