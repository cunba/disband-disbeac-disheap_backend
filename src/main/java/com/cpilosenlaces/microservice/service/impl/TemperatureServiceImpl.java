package com.cpilosenlaces.microservice.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpilosenlaces.microservice.exception.NotFoundException;
import com.cpilosenlaces.microservice.model.Temperature;
import com.cpilosenlaces.microservice.repository.TemperatureRepository;
import com.cpilosenlaces.microservice.service.TemperatureService;

@Service
public class TemperatureServiceImpl implements TemperatureService {

    @Autowired
    private TemperatureRepository tr;

    @Override
    public List<Temperature> findByDisbandId(UUID disbandId) {
        return tr.findByDisbandId(disbandId);
    }

    @Override
    public List<Temperature> findByDateBetween(long minDate, long maxDate) {
        return tr.findByDateBetween(minDate, maxDate);
    }

    @Override
    public List<Temperature> findByDateBetweenAndDisbandId(long minDate, long maxDate, UUID disbandId) {
        return tr.findByDateBetweenAndDisbandId(minDate, maxDate, disbandId);
    }

    @Override
    public Temperature findById(UUID id) throws NotFoundException {
        return tr.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<Temperature> findAll() {
        return tr.findAll();
    }

    @Override
    public Temperature save(Temperature temperature) {
        return tr.save(temperature);
    }

    @Override
    public void deleteByDisband(List<Temperature> listTemperature) {
        tr.deleteAll(listTemperature);
    }

}
