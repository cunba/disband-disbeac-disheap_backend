package com.cpilosenlaces.microservice.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cpilosenlaces.microservice.model.LocationDisbeac;

@Repository
public interface LocationDisbeacRepository extends CrudRepository<LocationDisbeac, UUID> {

    List<LocationDisbeac> findLast1ByDisbeacId(UUID disbeacId);

    List<LocationDisbeac> findByDateBetweenAndDisbeacId(long minDate, long maxDate, UUID disbeacId);

    List<LocationDisbeac> findByDisbeacId(UUID disbeacId);

    List<LocationDisbeac> findAll();

}