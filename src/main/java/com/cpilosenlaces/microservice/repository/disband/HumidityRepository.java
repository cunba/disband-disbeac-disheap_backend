package com.cpilosenlaces.microservice.repository.disband;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cpilosenlaces.microservice.model.disband.Humidity;

@Repository
public interface HumidityRepository extends JpaRepository<Humidity, UUID> {
    List<Humidity> findLast1ByDisbandIdOrderByDateDesc(UUID disbandId);

    List<Humidity> findByDisbandIdOrderByDateDesc(UUID disbandId);

    List<Humidity> findByDateBetweenAndDisbandIdOrderByDateDesc(long minDate, long maxDate, UUID disbandId);

    List<Humidity> findByDateBetweenOrderByDateDesc(long minDate, long maxDate);

    List<Humidity> findAll();
}
