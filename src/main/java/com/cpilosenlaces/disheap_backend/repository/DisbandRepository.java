package com.cpilosenlaces.disheap_backend.repository;

import java.util.List;
import java.util.UUID;

import com.cpilosenlaces.disheap_backend.model.Disband;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DisbandRepository extends CrudRepository<Disband, UUID> {
    List<Disband> findByUserId(UUID userId);

    List<Disband> findByMac(String mac);

    List<Disband> findAll();

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE disbands SET user_id = :userId WHERE id = :id", nativeQuery = true)
    void updateUserId(UUID userId, UUID id);
}
