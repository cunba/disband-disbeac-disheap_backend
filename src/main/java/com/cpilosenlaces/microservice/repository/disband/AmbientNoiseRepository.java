package com.cpilosenlaces.microservice.repository.disband;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cpilosenlaces.microservice.model.disband.AmbientNoise;

@Repository
public interface AmbientNoiseRepository extends JpaRepository<AmbientNoise, UUID> {
    @Query(value = "SELECT * FROM ambient_noises WHERE disband_id = :disbandId AND date BETWEEN :minDate AND :maxDate ORDER BY date DESC LIMIT 1", nativeQuery = true)
    AmbientNoise findLast1ByDateBetweenAndDisbandIdOrderByDateDesc(long minDate, long maxDate, UUID disbandId);
    
    @Query(value = "SELECT * FROM ambient_noises WHERE disband_id = :disbandId AND date BETWEEN :minDate AND :maxDate ORDER BY data DESC LIMIT 1", nativeQuery = true)
    AmbientNoise findMaxValueByDateBetweenAndDisbandId(long minDate, long maxDate, UUID disbandId);
    
    @Query(value = "SELECT * FROM ambient_noises WHERE disband_id = :disbandId AND date BETWEEN :minDate AND :maxDate ORDER BY data ASC LIMIT 1", nativeQuery = true)
    AmbientNoise findMinValueByDateBetweenAndDisbandId(long minDate, long maxDate, UUID disbandId);

    List<AmbientNoise> findByDisbandIdOrderByDateDesc(UUID disbandId);

    List<AmbientNoise> findByDateBetweenAndDisbandIdOrderByDateDesc(long minDate, long maxDate, UUID disbandId);

    List<AmbientNoise> findByDateBetweenOrderByDateDesc(long minDate, long maxDate);

    List<AmbientNoise> findAll();
}
