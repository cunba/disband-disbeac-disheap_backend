package com.cpilosenlaces.disheap_backend.repository;

import java.util.List;
import java.util.UUID;

import com.cpilosenlaces.disheap_backend.model.Lightning;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LightningRepository extends CrudRepository<Lightning, UUID> {
    List<Lightning> findByDisbandId(UUID disbandId);

    List<Lightning> findByDateBetweenAndDisbandId(long minDate, long maxDate, UUID disbandId);

    List<Lightning> findByDateBetween(long minDate, long maxDate);

    List<Lightning> findAll();
}
