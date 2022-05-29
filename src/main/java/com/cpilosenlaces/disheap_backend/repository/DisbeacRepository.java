package com.cpilosenlaces.disheap_backend.repository;

import java.util.List;
import java.util.UUID;

import com.cpilosenlaces.disheap_backend.model.Disbeac;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisbeacRepository extends CrudRepository<Disbeac, UUID> {
    List<Disbeac> findByUserId(UUID userId);

    List<Disbeac> findAll();
}
