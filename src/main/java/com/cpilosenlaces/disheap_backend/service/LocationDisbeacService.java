package com.cpilosenlaces.disheap_backend.service;

import java.util.List;
import java.util.UUID;

import com.cpilosenlaces.disheap_backend.exception.NotFoundException;
import com.cpilosenlaces.disheap_backend.model.LocationDisbeac;

public interface LocationDisbeacService {
    
    List<LocationDisbeac> findLast1ByDisbeacId(UUID disbeacId);

    List<LocationDisbeac> findByDateBetweenAndDisbeacId(long minDate, long maxDate, UUID disbeacId);

    List<LocationDisbeac> findByDisbeacId(UUID disbeacId);

    LocationDisbeac findById(UUID id) throws NotFoundException;

    List<LocationDisbeac> findAll();

    LocationDisbeac save(LocationDisbeac locationDisbeac);

    void delete(LocationDisbeac locationDisbeac);

    void deleteAll();
}
