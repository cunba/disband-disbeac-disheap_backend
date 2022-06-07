package com.cpilosenlaces.microservice.repository.disbeac;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cpilosenlaces.microservice.model.disbeac.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, UUID> {
    @Query(value = "SELECT * FROM locations WHERE disband_id = :disbandId AND date BETWEEN :minDate AND :maxDate ORDER BY date DESC LIMIT 1", nativeQuery = true)
    Location findLast1ByDateBetweenAndDisbandIdOrderByDateDesc(long minDate, long maxDate, UUID disbandId);

    List<Location> findByDateBetweenAndDisbeacIdOrderByDateDesc(long minDate, long maxDate, UUID disbeacId);

    List<Location> findByDisbeacIdOrderByDateDesc(UUID disbeacId);

    List<Location> findAll();

}