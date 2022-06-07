package com.cpilosenlaces.microservice.repository.disband;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cpilosenlaces.microservice.model.disband.Disband;

@Repository
public interface DisbandRepository extends JpaRepository<Disband, UUID> {
    List<Disband> findByUserId(UUID userId);

    List<Disband> findByMac(String mac);

    List<Disband> findAll();

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE disbands SET user_id = :userId WHERE id = :id", nativeQuery = true)
    void updateUserId(UUID userId, UUID id);
}
