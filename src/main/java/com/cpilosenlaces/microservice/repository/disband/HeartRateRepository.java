package com.cpilosenlaces.microservice.repository.disband;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cpilosenlaces.microservice.model.disband.HeartRate;

@Repository
public interface HeartRateRepository extends JpaRepository<HeartRate, UUID> {
    @Query(value = "SELECT * FROM heart_rates WHERE disband_id = :disbandId AND date BETWEEN :minDate AND :maxDate ORDER BY date DESC LIMIT 1", nativeQuery = true)
    HeartRate findLast1ByDateBetweenAndDisbandIdOrderByDateDesc(long minDate, long maxDate, UUID disbandId);

    List<HeartRate> findByDisbandIdOrderByDateDesc(UUID disbandId);

    List<HeartRate> findByDateBetweenAndDisbandIdOrderByDateDesc(long minDate, long maxDate, UUID disbandId);

    List<HeartRate> findByDateBetweenOrderByDateDesc(long minDate, long maxDate);

    List<HeartRate> findAll();
}
