package com.cpilosenlaces.microservice.repository.disband;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cpilosenlaces.microservice.model.disband.Lightning;

@Repository
public interface LightningRepository extends JpaRepository<Lightning, UUID> {
    List<Lightning> findLast1ByDisbandIdOrderByDateDesc(UUID disbandId);

    List<Lightning> findByDisbandIdOrderByDateDesc(UUID disbandId);

    List<Lightning> findByDateBetweenAndDisbandIdOrderByDateDesc(long minDate, long maxDate, UUID disbandId);

    List<Lightning> findByDateBetweenOrderByDateDesc(long minDate, long maxDate);

    List<Lightning> findAll();
}
