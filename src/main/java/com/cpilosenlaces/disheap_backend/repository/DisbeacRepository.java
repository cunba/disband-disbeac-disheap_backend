package com.cpilosenlaces.disheap_backend.repository;

import java.util.List;
import java.util.UUID;

import com.cpilosenlaces.disheap_backend.model.Disbeac;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DisbeacRepository extends CrudRepository<Disbeac, UUID> {
    List<Disbeac> findByUserId(UUID userId);

    List<Disbeac> findByMac(String mac);

    List<Disbeac> findAll();

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE disbeacs SET user_id = :userId WHERE id = :id", nativeQuery = true)
    void updateUserId(UUID userId, UUID id);
}
