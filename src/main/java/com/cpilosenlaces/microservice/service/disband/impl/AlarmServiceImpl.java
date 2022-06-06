package com.cpilosenlaces.microservice.service.disband.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cpilosenlaces.microservice.exception.NotFoundException;
import com.cpilosenlaces.microservice.model.disband.Alarm;
import com.cpilosenlaces.microservice.repository.disband.AlarmRepository;
import com.cpilosenlaces.microservice.service.disband.AlarmService;

@Service
public class AlarmServiceImpl implements AlarmService {

    @Autowired
    private AlarmRepository ar;

    @Override
    public List<Alarm> findByDisbandId(UUID disbandId) {
        return ar.findByDisbandIdOrderByDateDesc(disbandId);
    }

    @Override
    public List<Alarm> findByDateBetweenAndDisbandId(long minDate, long maxDate, UUID disbandId) {
        return ar.findByDateBetweenAndDisbandIdOrderByDateDesc(minDate, maxDate, disbandId);
    }

    @Override
    public Alarm findById(UUID id) throws NotFoundException {
        return ar.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<Alarm> findAll() {
        return ar.findAll(Sort.by(Sort.Direction.DESC, "date"));
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
    public void deleteByDisband(List<Alarm> alarms) {
        ar.deleteAll(alarms);
    }

}
