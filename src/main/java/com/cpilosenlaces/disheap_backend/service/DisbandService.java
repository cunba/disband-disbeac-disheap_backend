package com.cpilosenlaces.disheap_backend.service;

import java.util.List;
import java.util.UUID;

import com.cpilosenlaces.disheap_backend.exception.NotFoundException;
import com.cpilosenlaces.disheap_backend.model.Disband;

public interface DisbandService {
    List<Disband> findByUserId(UUID userId);

    List<Disband> findByMac(String mac);

    Disband findById(UUID id) throws NotFoundException;

    List<Disband> findAll();

    Disband save(Disband disband);

    void delete(Disband disband);

    void deleteAll();
}
