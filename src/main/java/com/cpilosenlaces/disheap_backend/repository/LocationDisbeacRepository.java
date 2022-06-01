package com.cpilosenlaces.disheap_backend.repository;

import java.util.List;
import java.util.UUID;

import com.cpilosenlaces.disheap_backend.model.LocationDisbeac;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationDisbeacRepository extends CrudRepository<LocationDisbeac, UUID> {

    List<LocationDisbeac> findLast1ByDisbeacId(UUID disbeacId);

    List<LocationDisbeac> findByDateBetweenAndDisbeacId(long minDate, long maxDate, UUID disbeacId);

    List<LocationDisbeac> findByDisbeacId(UUID disbeacId);

    List<LocationDisbeac> findAll();

}