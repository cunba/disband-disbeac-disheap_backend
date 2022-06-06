package com.cpilosenlaces.microservice.service.disbeac;

import java.util.List;
import java.util.UUID;

import com.cpilosenlaces.microservice.exception.NotFoundException;
import com.cpilosenlaces.microservice.model.disbeac.Disbeac;

public interface DisbeacService {
    List<Disbeac> findByUserId(UUID userId);

    List<Disbeac> findByMac(String mac);

    Disbeac findById(UUID id) throws NotFoundException;

    List<Disbeac> findAll();

    Disbeac save(Disbeac disbeac);

    void updateUserId(UUID userId, UUID id);

    void delete(Disbeac disbeac);

    void deleteByUser(List<Disbeac> disbeacs);
}
