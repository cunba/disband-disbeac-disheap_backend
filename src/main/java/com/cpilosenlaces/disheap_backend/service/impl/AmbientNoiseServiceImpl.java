package com.cpilosenlaces.disheap_backend.service.impl;

import java.util.List;
import java.util.UUID;

import com.cpilosenlaces.disheap_backend.exception.NotFoundException;
import com.cpilosenlaces.disheap_backend.model.AmbientNoise;
import com.cpilosenlaces.disheap_backend.repository.AmbientNoiseRepository;
import com.cpilosenlaces.disheap_backend.service.AmbientNoiseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AmbientNoiseServiceImpl implements AmbientNoiseService {

    @Autowired
    private AmbientNoiseRepository anr;

    @Override
    public List<AmbientNoise> findByDisbandId(UUID disbandId) {
        return anr.findByDisbandId(disbandId);
    }

    @Override
    public List<AmbientNoise> findByDateBetween(long minDate, long maxDate) {
        return anr.findByDateBetween(minDate, maxDate);
    }

    @Override
    public List<AmbientNoise> findByDateBetweenAndDisbandId(long minDate, long maxDate, UUID disbandId) {
        return anr.findByDateBetweenAndDisbandId(minDate, maxDate, disbandId);
    }

    @Override
    public AmbientNoise findById(UUID id) throws NotFoundException {
        return anr.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<AmbientNoise> findAll() {
        return anr.findAll();
    }

    @Override
    public AmbientNoise save(AmbientNoise AmbientNoise) {
        return anr.save(AmbientNoise);
    }

    @Override
    public void deleteByDisband(List<AmbientNoise> listAmbientNoise) {
        anr.deleteAll(listAmbientNoise);
    }

}
