package com.cpilosenlaces.microservice.repository.disband;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cpilosenlaces.microservice.model.disband.Oxygen;

@Repository
public interface OxygenRepository extends JpaRepository<Oxygen, UUID> {
    List<Oxygen> findLast1ByDisbandIdOrderByDateDesc(UUID disbandId);

    List<Oxygen> findByDisbandIdOrderByDateDesc(UUID disbandId);

    List<Oxygen> findByDateBetweenAndDisbandIdOrderByDateDesc(long minDate, long maxDate, UUID disbandId);

    List<Oxygen> findByDateBetweenOrderByDateDesc(long minDate, long maxDate);

    List<Oxygen> findAll();
}
