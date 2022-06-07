package com.cpilosenlaces.microservice.service.disband.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cpilosenlaces.microservice.exception.NotFoundException;
import com.cpilosenlaces.microservice.model.disband.Temperature;
import com.cpilosenlaces.microservice.repository.disband.TemperatureRepository;
import com.cpilosenlaces.microservice.service.disband.TemperatureService;

@Service
public class TemperatureServiceImpl implements TemperatureService {

    @Autowired
    private TemperatureRepository tr;

    @Override
    public Temperature findLast1ByDateBetweenAndDisbandId(long minDate, long maxDate, UUID disbandId) {
        return tr.findLast1ByDateBetweenAndDisbandIdOrderByDateDesc(minDate, maxDate, disbandId);
    }

    @Override
    public List<Temperature> findByDisbandId(UUID disbandId) {
        return tr.findByDisbandIdOrderByDateDesc(disbandId);
    }

    @Override
    public List<Temperature> findByDateBetween(long minDate, long maxDate) {
        return tr.findByDateBetweenOrderByDateDesc(minDate, maxDate);
    }

    @Override
    public List<Temperature> findByDateBetweenAndDisbandId(long minDate, long maxDate, UUID disbandId) {
        return tr.findByDateBetweenAndDisbandIdOrderByDateDesc(minDate, maxDate, disbandId);
    }

    @Override
    public Temperature findById(UUID id) throws NotFoundException {
        return tr.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<Temperature> findAll() {
        return tr.findAll(Sort.by(Sort.Direction.DESC, "date"));
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
