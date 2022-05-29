package com.cpilosenlaces.disheap_backend.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.cpilosenlaces.disheap_backend.exception.NotFoundException;
import com.cpilosenlaces.disheap_backend.model.Alarm;
import com.cpilosenlaces.disheap_backend.repository.AlarmRepository;
import com.cpilosenlaces.disheap_backend.service.AlarmService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlarmServiceImpl implements AlarmService {

    @Autowired
    private AlarmRepository ar;

    @Override
    public List<Alarm> findByDisbandId(UUID disbandId) {
        return ar.findByDisbandId(disbandId);
    }

    @Override
    public List<Alarm> findByDateBetweenAndDisbandId(LocalDateTime minDate, LocalDateTime maxDate, UUID disbandId) {
        return ar.findByDateBetweenAndDisbandId(minDate, maxDate, disbandId);
    }

    @Override
    public Alarm findById(UUID id) throws NotFoundException {
        return ar.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<Alarm> findAll() {
        return ar.findAll();
    }

    @Override
    public Alarm save(Alarm alarm) {
        return ar.save(alarm);
    }

    @Override
    public void delete(Alarm alarm) {
        ar.delete(alarm);
    }

    @Override
    public void deleteAll() {
        ar.deleteAll();
    }

}
