package com.cpilosenlaces.microservice.service.disbeac;

import java.util.List;
import java.util.UUID;

import com.cpilosenlaces.microservice.exception.NotFoundException;
import com.cpilosenlaces.microservice.model.disbeac.Location;

public interface LocationService {
    Location findLast1ByDateBetweenAndDisbeacId(long minDate, long maxDate, UUID disbeacId);

    List<Location> findByDateBetweenAndDisbeacId(long minDate, long maxDate, UUID disbeacId);

    List<Location> findByDisbeacId(UUID disbeacId);

    Location findById(UUID id) throws NotFoundException;

    List<Location> findAll();

    Location save(Location location);

    void delete(Location location);

    void deleteByDisbeac(List<Location> listLocations);
}
