package com.cpilosenlaces.disheap_backend.service;

import java.util.List;
import java.util.UUID;

import com.cpilosenlaces.disheap_backend.exception.NotFoundException;
import com.cpilosenlaces.disheap_backend.model.Disbeac;

public interface DisbeacService {
    List<Disbeac> findByUserId(UUID userId);

    Disbeac findById(UUID id) throws NotFoundException;

    List<Disbeac> findAll();

    Disbeac save(Disbeac disbeac);

    void delete(Disbeac disbeac);

    void deleteAll();
}
