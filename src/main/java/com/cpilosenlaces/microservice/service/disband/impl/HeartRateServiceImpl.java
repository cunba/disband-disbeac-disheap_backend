package com.cpilosenlaces.microservice.service.disband.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cpilosenlaces.microservice.exception.NotFoundException;
import com.cpilosenlaces.microservice.model.disband.HeartRate;
import com.cpilosenlaces.microservice.repository.disband.HeartRateRepository;
import com.cpilosenlaces.microservice.service.disband.HeartRateService;

@Service
public class HeartRateServiceImpl implements HeartRateService {

    @Autowired
    private HeartRateRepository hr;

    @Override
    public HeartRate findLast1ByDisbandId(long minDate, long maxDate, UUID disbandId) {
        return hr.findLast1ByDisbandIdOrderByDateDesc(minDate, maxDate, disbandId);
    }

    @Override
    public List<HeartRate> findByDisbandId(UUID disbandId) {
        return hr.findByDisbandIdOrderByDateDesc(disbandId);
    }

    @Override
    public List<HeartRate> findByDateBetween(long minDate, long maxDate) {
        return hr.findByDateBetweenOrderByDateDesc(minDate, maxDate);
    }

    @Override
    public List<HeartRate> findByDateBetweenAndDisbandId(long minDate, long maxDate, UUID disbandId) {
        return hr.findByDateBetweenAndDisbandIdOrderByDateDesc(minDate, maxDate, disbandId);
    }

    @Override
    public HeartRate findById(UUID id) throws NotFoundException {
        return hr.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<HeartRate> findAll() {
        return hr.findAll(Sort.by(Sort.Direction.DESC, "date"));
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
