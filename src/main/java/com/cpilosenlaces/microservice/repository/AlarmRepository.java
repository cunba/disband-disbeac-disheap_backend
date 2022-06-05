package com.cpilosenlaces.microservice.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cpilosenlaces.microservice.model.Alarm;

@Repository
public interface AlarmRepository extends CrudRepository<Alarm, UUID> {
    List<Alarm> findByDisbandId(UUID disbandId);

    List<Alarm> findByDateBetweenAndDisbandId(long minDate, long maxDate, UUID disbandId);

    List<Alarm> findAll();
}
