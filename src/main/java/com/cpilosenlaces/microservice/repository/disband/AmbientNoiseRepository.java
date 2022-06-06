package com.cpilosenlaces.microservice.repository.disband;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cpilosenlaces.microservice.model.disband.AmbientNoise;

@Repository
public interface AmbientNoiseRepository extends JpaRepository<AmbientNoise, UUID> {
    List<AmbientNoise> findLast1ByDisbandIdOrderByDateDesc(UUID disbandId);

    List<AmbientNoise> findByDisbandIdOrderByDateDesc(UUID disbandId);

    List<AmbientNoise> findByDateBetweenAndDisbandIdOrderByDateDesc(long minDate, long maxDate, UUID disbandId);

    List<AmbientNoise> findByDateBetweenOrderByDateDesc(long minDate, long maxDate);

    List<AmbientNoise> findAll();
}
