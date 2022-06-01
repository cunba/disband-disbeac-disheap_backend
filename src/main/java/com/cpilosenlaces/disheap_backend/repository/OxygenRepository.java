package com.cpilosenlaces.disheap_backend.repository;

import java.util.List;
import java.util.UUID;

import com.cpilosenlaces.disheap_backend.model.Oxygen;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OxygenRepository extends CrudRepository<Oxygen, UUID> {
    List<Oxygen> findByDisbandId(UUID disbandId);

    List<Oxygen> findByDateBetweenAndDisbandId(long minDate, long maxDate, UUID disbandId);

    List<Oxygen> findByDateBetween(long minDate, long maxDate);

    List<Oxygen> findAll();
}
