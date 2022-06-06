package com.cpilosenlaces.microservice.repository.disbeac;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cpilosenlaces.microservice.model.disbeac.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, UUID> {

    List<Location> findLast1ByDisbeacIdOrderByDateDesc(UUID disbeacId);

    List<Location> findByDateBetweenAndDisbeacIdOrderByDateDesc(long minDate, long maxDate, UUID disbeacId);

    List<Location> findByDisbeacIdOrderByDateDesc(UUID disbeacId);

    List<Location> findAll();

}