package com.cpilosenlaces.microservice.service.disband.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cpilosenlaces.microservice.exception.NotFoundException;
import com.cpilosenlaces.microservice.model.disband.AmbientNoise;
import com.cpilosenlaces.microservice.repository.disband.AmbientNoiseRepository;
import com.cpilosenlaces.microservice.service.disband.AmbientNoiseService;

@Service
public class AmbientNoiseServiceImpl implements AmbientNoiseService {

    @Autowired
    private AmbientNoiseRepository anr;

    @Override
    public List<AmbientNoise> findByDisbandId(UUID disbandId) {
        return anr.findByDisbandIdOrderByDateDesc(disbandId);
    }

    @Override
    public List<AmbientNoise> findByDateBetween(long minDate, long maxDate) {
        return anr.findByDateBetweenOrderByDateDesc(minDate, maxDate);
    }

    @Override
    public List<AmbientNoise> findByDateBetweenAndDisbandId(long minDate, long maxDate, UUID disbandId) {
        return anr.findByDateBetweenAndDisbandIdOrderByDateDesc(minDate, maxDate, disbandId);
    }

    @Override
    public AmbientNoise findById(UUID id) throws NotFoundException {
        return anr.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<AmbientNoise> findAll() {
        return anr.findAll(Sort.by(Sort.Direction.DESC, "date"));
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
