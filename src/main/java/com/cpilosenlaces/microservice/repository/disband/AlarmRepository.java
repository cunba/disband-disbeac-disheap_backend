package com.cpilosenlaces.microservice.repository.disband;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cpilosenlaces.microservice.model.disband.Alarm;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, UUID> {
    List<Alarm> findByDisbandIdOrderByDateDesc(UUID disbandId);

    List<Alarm> findByDateBetweenAndDisbandIdOrderByDateDesc(long minDate, long maxDate, UUID disbandId);

    List<Alarm> findAll();
}
