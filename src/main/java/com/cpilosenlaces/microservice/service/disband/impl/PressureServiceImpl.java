package com.cpilosenlaces.microservice.service.disband.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cpilosenlaces.microservice.exception.NotFoundException;
import com.cpilosenlaces.microservice.model.disband.Pressure;
import com.cpilosenlaces.microservice.repository.disband.PressureRepository;
import com.cpilosenlaces.microservice.service.disband.PressureService;

@Service
public class PressureServiceImpl implements PressureService {

    @Autowired
    private PressureRepository pr;

    @Override
    public Pressure findLast1ByDateBetweenAndDisbandId(long minDate, long maxDate, UUID disbandId) {
        return pr.findLast1ByDateBetweenAndDisbandIdOrderByDateDesc(minDate, maxDate, disbandId);
    }

    @Override
    public Pressure findMinValueByDateBetweenAndDisbandId(long minDate, long maxDate, UUID disbandId) {
        return pr.findMinValueByDateBetweenAndDisbandId(minDate, maxDate, disbandId);
    }

    @Override
    public Pressure findMaxValueByDateBetweenAndDisbandId(long minDate, long maxDate, UUID disbandId) {
        return pr.findMaxValueByDateBetweenAndDisbandId(minDate, maxDate, disbandId);
    }

    @Override
    public List<Pressure> findByDisbandId(UUID disbandId) {
        return pr.findByDisbandIdOrderByDateDesc(disbandId);
    }

    @Override
    public List<Pressure> findByDateBetween(long minDate, long maxDate) {
        return pr.findByDateBetweenOrderByDateDesc(minDate, maxDate);
    }

    @Override
    public List<Pressure> findByDateBetweenAndDisbandId(long minDate, long maxDate, UUID disbandId) {
        return pr.findByDateBetweenAndDisbandIdOrderByDateDesc(minDate, maxDate, disbandId);
    }

    @Override
    public Pressure findById(UUID id) throws NotFoundException {
        return pr.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<Pressure> findAll() {
        return pr.findAll(Sort.by(Sort.Direction.DESC, "date"));
    }

    @Override
    public Pressure save(Pressure pressure) {
        return pr.save(pressure);
    }

    @Override
    public void deleteByDisband(List<Pressure> listPressure) {
        pr.deleteAll(listPressure);
    }

}
