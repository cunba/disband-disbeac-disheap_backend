package com.cpilosenlaces.microservice.repository.disband;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cpilosenlaces.microservice.model.disband.Pressure;

@Repository
public interface PressureRepository extends JpaRepository<Pressure, UUID> {
    @Query(value = "SELECT * FROM pressures WHERE disband_id = :disbandId AND date BETWEEN :minDate AND :maxDate ORDER BY date DESC LIMIT 1", nativeQuery = true)
    Pressure findLast1ByDateBetweenAndDisbandIdOrderByDateDesc(long minDate, long maxDate, UUID disbandId);

    List<Pressure> findByDisbandIdOrderByDateDesc(UUID disbandId);

    List<Pressure> findByDateBetweenAndDisbandIdOrderByDateDesc(long minDate, long maxDate, UUID disbandId);

    List<Pressure> findByDateBetweenOrderByDateDesc(long minDate, long maxDate);

    List<Pressure> findAll();
}
