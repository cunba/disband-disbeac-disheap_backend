package com.cpilosenlaces.disheap_backend.repository;

import java.util.List;
import java.util.UUID;

import com.cpilosenlaces.disheap_backend.model.Alarm;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmRepository extends CrudRepository<Alarm, UUID> {
    List<Alarm> findByDisbandId(UUID disbandId);

    List<Alarm> findByDateBetweenAndDisbandId(long minDate, long maxDate, UUID disbandId);

    List<Alarm> findAll();
}
