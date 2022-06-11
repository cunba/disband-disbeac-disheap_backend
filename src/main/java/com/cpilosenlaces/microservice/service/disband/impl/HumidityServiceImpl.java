package com.cpilosenlaces.microservice.service.disband.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cpilosenlaces.microservice.exception.NotFoundException;
import com.cpilosenlaces.microservice.model.disband.Humidity;
import com.cpilosenlaces.microservice.repository.disband.HumidityRepository;
import com.cpilosenlaces.microservice.service.disband.HumidityService;

@Service
public class HumidityServiceImpl implements HumidityService {

    @Autowired
    private HumidityRepository hr;

    @Override
    public Humidity findLast1ByDateBetweenAndDisbandId(long minDate, long maxDate, UUID disbandId) {
        return hr.findLast1ByDateBetweenAndDisbandIdOrderByDateDesc(minDate, maxDate, disbandId);
    }

    @Override
    public Humidity findMinValueByDateBetweenAndDisbandId(long minDate, long maxDate, UUID disbandId) {
        return hr.findMinValueByDateBetweenAndDisbandId(minDate, maxDate, disbandId);
    }

    @Override
    public Humidity findMaxValueByDateBetweenAndDisbandId(long minDate, long maxDate, UUID disbandId) {
        return hr.findMaxValueByDateBetweenAndDisbandId(minDate, maxDate, disbandId);
    }

    @Override
    public List<Humidity> findByDisbandId(UUID disbandId) {
        return hr.findByDisbandIdOrderByDateDesc(disbandId);
    }

    @Override
    public List<Humidity> findByDateBetween(long minDate, long maxDate) {
        return hr.findByDateBetweenOrderByDateDesc(minDate, maxDate);
    }

    @Override
    public List<Humidity> findByDateBetweenAndDisbandId(long minDate, long maxDate, UUID disbandId) {
        return hr.findByDateBetweenAndDisbandIdOrderByDateDesc(minDate, maxDate, disbandId);
    }

    @Override
    public Humidity findById(UUID id) throws NotFoundException {
        return hr.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<Humidity> findAll() {
        return hr.findAll(Sort.by(Sort.Direction.DESC, "date"));
    }

    @Override
    public Humidity save(Humidity humidity) {
        return hr.save(humidity);
    }

    @Override
    public void deleteByDisband(List<Humidity> listHumidity) {
        hr.deleteAll(listHumidity);
    }

}
