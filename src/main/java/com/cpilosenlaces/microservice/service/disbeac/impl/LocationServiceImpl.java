package com.cpilosenlaces.microservice.service. disbeac.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cpilosenlaces.microservice.exception.NotFoundException;
import com.cpilosenlaces.microservice.model.disbeac.Location;
import com.cpilosenlaces.microservice.repository.disbeac.LocationRepository;
import com.cpilosenlaces.microservice.service.disbeac.LocationService;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationRepository ldr;

    @Override
    public List<Location> findLast1ByDisbeacId(UUID disbeacId) {
        return ldr.findLast1ByDisbeacIdOrderByDateDesc(disbeacId);
    }

    @Override
    public List<Location> findByDateBetweenAndDisbeacId(long minDate, long maxDate,
            UUID disbeacId) {

        return ldr.findByDateBetweenAndDisbeacIdOrderByDateDesc(minDate, maxDate, disbeacId);
    }

    @Override
    public List<Location> findByDisbeacId(UUID disbeacId) {
        return ldr.findByDisbeacIdOrderByDateDesc(disbeacId);
    }

    @Override
    public Location findById(UUID id) throws NotFoundException {
        return ldr.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<Location> findAll() {
        return ldr.findAll(Sort.by(Sort.Direction.DESC, "date"));
    }

    @Override
    public Location save(Location location) {
        return ldr.save(location);
    }

    @Override
    public void delete(Location location) {
        ldr.delete(location);
    }

    @Override
    public void deleteByDisbeac(List<Location> listLocations) {
        ldr.deleteAll(listLocations);
    }

}
