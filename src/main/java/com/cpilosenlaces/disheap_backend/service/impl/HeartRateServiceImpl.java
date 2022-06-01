package com.cpilosenlaces.disheap_backend.service.impl;

import java.util.List;
import java.util.UUID;

import com.cpilosenlaces.disheap_backend.exception.NotFoundException;
import com.cpilosenlaces.disheap_backend.model.HeartRate;
import com.cpilosenlaces.disheap_backend.repository.HeartRateRepository;
import com.cpilosenlaces.disheap_backend.service.HeartRateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HeartRateServiceImpl implements HeartRateService {

    @Autowired
    private HeartRateRepository hr;

    @Override
    public List<HeartRate> findByDisbandId(UUID disbandId) {
        return hr.findByDisbandId(disbandId);
    }

    @Override
    public List<HeartRate> findByDateBetween(long minDate, long maxDate) {
        return hr.findByDateBetween(minDate, maxDate);
    }

    @Override
    public List<HeartRate> findByDateBetweenAndDisbandId(long minDate, long maxDate, UUID disbandId) {
        return hr.findByDateBetweenAndDisbandId(minDate, maxDate, disbandId);
    }

    @Override
    public HeartRate findById(UUID id) throws NotFoundException {
        return hr.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<HeartRate> findAll() {
        return hr.findAll();
    }

    @Override
    public HeartRate save(HeartRate heartRate) {
        return hr.save(heartRate);
    }

    @Override
    public void deleteByDisband(List<HeartRate> listHeartRate) {
        hr.deleteAll(listHeartRate);
    }

}
