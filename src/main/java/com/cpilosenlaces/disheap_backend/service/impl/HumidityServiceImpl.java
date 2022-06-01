package com.cpilosenlaces.disheap_backend.service.impl;

import java.util.List;
import java.util.UUID;

import com.cpilosenlaces.disheap_backend.exception.NotFoundException;
import com.cpilosenlaces.disheap_backend.model.Humidity;
import com.cpilosenlaces.disheap_backend.repository.HumidityRepository;
import com.cpilosenlaces.disheap_backend.service.HumidityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HumidityServiceImpl implements HumidityService {

    @Autowired
    private HumidityRepository hr;

    @Override
    public List<Humidity> findByDisbandId(UUID disbandId) {
        return hr.findByDisbandId(disbandId);
    }

    @Override
    public List<Humidity> findByDateBetween(long minDate, long maxDate) {
        return hr.findByDateBetween(minDate, maxDate);
    }

    @Override
    public List<Humidity> findByDateBetweenAndDisbandId(long minDate, long maxDate, UUID disbandId) {
        return hr.findByDateBetweenAndDisbandId(minDate, maxDate, disbandId);
    }

    @Override
    public Humidity findById(UUID id) throws NotFoundException {
        return hr.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<Humidity> findAll() {
        return hr.findAll();
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
