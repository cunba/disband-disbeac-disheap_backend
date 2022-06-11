package com.cpilosenlaces.microservice.service.disband;

import java.util.List;
import java.util.UUID;

import com.cpilosenlaces.microservice.exception.NotFoundException;
import com.cpilosenlaces.microservice.model.disband.Pressure;

public interface PressureService {
    Pressure findLast1ByDateBetweenAndDisbandId(long minDate, long maxDate, UUID disbandId);
    
    Pressure findMaxValueByDateBetweenAndDisbandId(long minDate, long maxDate, UUID disbandId);
    
    Pressure findMinValueByDateBetweenAndDisbandId(long minDate, long maxDate, UUID disbandId);

    List<Pressure> findByDisbandId(UUID disbandId);

    List<Pressure> findByDateBetweenAndDisbandId(long minDate, long maxDate, UUID disbandId);

    List<Pressure> findByDateBetween(long minDate, long maxDate);

    Pressure findById(UUID id) throws NotFoundException;

    List<Pressure> findAll();

    Pressure save(Pressure pressure);

    void deleteByDisband(List<Pressure> listPressure);
}
