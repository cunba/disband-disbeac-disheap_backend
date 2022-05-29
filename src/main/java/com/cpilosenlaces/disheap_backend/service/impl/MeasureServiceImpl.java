package com.cpilosenlaces.disheap_backend.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.cpilosenlaces.disheap_backend.exception.NotFoundException;
import com.cpilosenlaces.disheap_backend.model.Measure;
import com.cpilosenlaces.disheap_backend.repository.MeasureRepository;
import com.cpilosenlaces.disheap_backend.service.MeasureService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MeasureServiceImpl implements MeasureService {

    @Autowired
    private MeasureRepository mr;

    @Override
    public List<Measure> findByDisbandId(UUID disbandId) {
        return mr.findByDisbandId(disbandId);
    }

    @Override
    public List<Measure> findByDateBetweenAndDisbandId(LocalDateTime minDate, LocalDateTime maxDate, UUID disbandId) {
        return mr.findByDateBetweenAndDisbandId(minDate, maxDate, disbandId);
    }

    @Override
    public Measure findById(UUID id) throws NotFoundException {
        return mr.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<Measure> findAll() {
        return mr.findAll();
    }

    @Override
    public Measure save(Measure measure) {
        return mr.save(measure);
    }

    @Override
    public void delete(Measure measure) {
        mr.delete(measure);
    }

    @Override
    public void deleteAll() {
        mr.deleteAll();
    }

}
