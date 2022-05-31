package com.cpilosenlaces.disheap_backend.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.cpilosenlaces.disheap_backend.exception.NotFoundException;
import com.cpilosenlaces.disheap_backend.model.LocationDisbeac;
import com.cpilosenlaces.disheap_backend.repository.LocationDisbeacRepository;
import com.cpilosenlaces.disheap_backend.service.LocationDisbeacService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationDisbeacServiceImpl implements LocationDisbeacService {

    @Autowired
    private LocationDisbeacRepository ldr;

    @Override
    public List<LocationDisbeac> findLast1ByDisbeacId(UUID disbeacId) {
        return ldr.findLast1ByDisbeacId(disbeacId);
    }

    @Override
    public List<LocationDisbeac> findByDateBetweenAndDisbeacId(LocalDateTime minDate, LocalDateTime maxDate,
            UUID disbeacId) {

        return ldr.findByDateBetweenAndDisbeacId(minDate, maxDate, disbeacId);
    }

    @Override
    public List<LocationDisbeac> findByDisbeacId(UUID disbeacId) {
        return ldr.findByDisbeacId(disbeacId);
    }

    @Override
    public LocationDisbeac findById(UUID id) throws NotFoundException {
        return ldr.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<LocationDisbeac> findAll() {
        return ldr.findAll();
    }

    @Override
    public LocationDisbeac save(LocationDisbeac locationDisbeac) {
        return ldr.save(locationDisbeac);
    }

    @Override
    public void delete(LocationDisbeac locationDisbeac) {
        ldr.delete(locationDisbeac);
    }

    @Override
    public void deleteAll() {
        ldr.deleteAll();
    }

}
