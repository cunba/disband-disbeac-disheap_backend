package com.cpilosenlaces.microservice.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cpilosenlaces.microservice.model.Oxygen;

@Repository
public interface OxygenRepository extends CrudRepository<Oxygen, UUID> {
    List<Oxygen> findByDisbandId(UUID disbandId);

    List<Oxygen> findByDateBetweenAndDisbandId(long minDate, long maxDate, UUID disbandId);

    List<Oxygen> findByDateBetween(long minDate, long maxDate);

    List<Oxygen> findAll();
}
