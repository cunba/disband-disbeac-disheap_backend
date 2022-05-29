package com.cpilosenlaces.disheap_backend.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.cpilosenlaces.disheap_backend.model.Alarm;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmRepository extends CrudRepository<Alarm, UUID> {
    List<Alarm> findByDisbandId(UUID disbandId);

    List<Alarm> findByDateBetweenAndDisbandId(LocalDateTime minDate, LocalDateTime maxDate, UUID disbandId);

    List<Alarm> findAll();
}
