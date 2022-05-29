package com.cpilosenlaces.disheap_backend.repository;

import java.util.List;
import java.util.UUID;

import com.cpilosenlaces.disheap_backend.model.Disband;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisbandRepository extends CrudRepository<Disband, UUID> {
    List<Disband> findByUserId(UUID userId);

    List<Disband> findAll();
}
