package com.cpilosenlaces.microservice.repository.disband;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cpilosenlaces.microservice.model.disband.Temperature;

@Repository
public interface TemperatureRepository extends JpaRepository<Temperature, UUID> {
    List<Temperature> findLast1ByDisbandIdOrderByDateDesc(UUID disbandId);

    List<Temperature> findByDisbandIdOrderByDateDesc(UUID disbandId);

    List<Temperature> findByDateBetweenAndDisbandIdOrderByDateDesc(long minDate, long maxDate, UUID disbandId);

    List<Temperature> findByDateBetweenOrderByDateDesc(long minDate, long maxDate);

    List<Temperature> findAll();
}
