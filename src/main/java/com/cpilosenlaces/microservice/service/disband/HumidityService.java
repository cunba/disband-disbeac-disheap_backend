package com.cpilosenlaces.microservice.service.disband;

import java.util.List;
import java.util.UUID;

import com.cpilosenlaces.microservice.exception.NotFoundException;
import com.cpilosenlaces.microservice.model.disband.Humidity;

public interface HumidityService {
    Humidity findLast1ByDisbandId(long minDate, long maxDate, UUID disbandId);

    List<Humidity> findByDisbandId(UUID disbandId);

    List<Humidity> findByDateBetweenAndDisbandId(long minDate, long maxDate, UUID disbandId);

    List<Humidity> findByDateBetween(long minDate, long maxDate);

    Humidity findById(UUID id) throws NotFoundException;

    List<Humidity> findAll();

    Humidity save(Humidity humidity);

    void deleteByDisband(List<Humidity> listHumidity);
}
