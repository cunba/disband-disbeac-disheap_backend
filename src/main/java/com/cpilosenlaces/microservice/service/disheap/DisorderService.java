package com.cpilosenlaces.microservice.service.disheap;

import java.util.List;
import java.util.UUID;

import com.cpilosenlaces.microservice.exception.NotFoundException;
import com.cpilosenlaces.microservice.model.disheap.Disorder;

public interface DisorderService {
    List<Disorder> findAll();

    Disorder findById(UUID id) throws NotFoundException;

    Disorder save(Disorder disorder);

    void deleteAll();
}
