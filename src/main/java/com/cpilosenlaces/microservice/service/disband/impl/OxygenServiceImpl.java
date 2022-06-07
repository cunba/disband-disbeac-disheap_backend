package com.cpilosenlaces.microservice.service.disband.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cpilosenlaces.microservice.exception.NotFoundException;
import com.cpilosenlaces.microservice.model.disband.Oxygen;
import com.cpilosenlaces.microservice.repository.disband.OxygenRepository;
import com.cpilosenlaces.microservice.service.disband.OxygenService;

@Service
public class OxygenServiceImpl implements OxygenService {

    @Autowired
    private OxygenRepository or;

    @Override
    public Oxygen findLast1ByDateBetweenAndDisbandId(long minDate, long maxDate, UUID disbandId) {
        return or.findLast1ByDateBetweenAndDisbandIdOrderByDateDesc(minDate, maxDate, disbandId);
    }

    @Override
    public List<Oxygen> findByDisbandId(UUID disbandId) {
        return or.findByDisbandIdOrderByDateDesc(disbandId);
    }

    @Override
    public List<Oxygen> findByDateBetween(long minDate, long maxDate) {
        return or.findByDateBetweenOrderByDateDesc(minDate, maxDate);
    }

    @Override
    public List<Oxygen> findByDateBetweenAndDisbandId(long minDate, long maxDate, UUID disbandId) {
        return or.findByDateBetweenAndDisbandIdOrderByDateDesc(minDate, maxDate, disbandId);
    }

    @Override
    public Oxygen findById(UUID id) throws NotFoundException {
        return or.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<Oxygen> findAll() {
        return or.findAll(Sort.by(Sort.Direction.DESC, "date"));
    }

    @Override
    public Oxygen save(Oxygen oxygen) {
        return or.save(oxygen);
    }

    @Override
    public void deleteByDisband(List<Oxygen> listOxygen) {
        or.deleteAll(listOxygen);
    }

}
