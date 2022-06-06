package com.cpilosenlaces.microservice.service.disband.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cpilosenlaces.microservice.exception.NotFoundException;
import com.cpilosenlaces.microservice.model.disband.Lightning;
import com.cpilosenlaces.microservice.repository.disband.LightningRepository;
import com.cpilosenlaces.microservice.service.disband.LightningService;

@Service
public class LightningServiceImpl implements LightningService {

    @Autowired
    private LightningRepository lr;

    @Override
    public List<Lightning> findByDisbandId(UUID disbandId) {
        return lr.findByDisbandIdOrderByDateDesc(disbandId);
    }

    @Override
    public List<Lightning> findByDateBetween(long minDate, long maxDate) {
        return lr.findByDateBetweenOrderByDateDesc(minDate, maxDate);
    }

    @Override
    public List<Lightning> findByDateBetweenAndDisbandId(long minDate, long maxDate, UUID disbandId) {
        return lr.findByDateBetweenAndDisbandIdOrderByDateDesc(minDate, maxDate, disbandId);
    }

    @Override
    public Lightning findById(UUID id) throws NotFoundException {
        return lr.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<Lightning> findAll() {
        return lr.findAll(Sort.by(Sort.Direction.DESC, "date"));
    }

    @Override
    public Lightning save(Lightning lightning) {
        return lr.save(lightning);
    }

    @Override
    public void deleteByDisband(List<Lightning> listLightning) {
        lr.deleteAll(listLightning);
    }

}
