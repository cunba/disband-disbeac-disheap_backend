package com.cpilosenlaces.disheap_backend.service.impl;

import java.util.List;
import java.util.UUID;

import com.cpilosenlaces.disheap_backend.exception.NotFoundException;
import com.cpilosenlaces.disheap_backend.model.Pressure;
import com.cpilosenlaces.disheap_backend.repository.PressureRepository;
import com.cpilosenlaces.disheap_backend.service.PressureService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PressureServiceImpl implements PressureService {

    @Autowired
    private PressureRepository pr;

    @Override
    public List<Pressure> findByDisbandId(UUID disbandId) {
        return pr.findByDisbandId(disbandId);
    }

    @Override
    public List<Pressure> findByDateBetween(long minDate, long maxDate) {
        return pr.findByDateBetween(minDate, maxDate);
    }

    @Override
    public List<Pressure> findByDateBetweenAndDisbandId(long minDate, long maxDate, UUID disbandId) {
        return pr.findByDateBetweenAndDisbandId(minDate, maxDate, disbandId);
    }

    @Override
    public Pressure findById(UUID id) throws NotFoundException {
        return pr.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<Pressure> findAll() {
        return pr.findAll();
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
