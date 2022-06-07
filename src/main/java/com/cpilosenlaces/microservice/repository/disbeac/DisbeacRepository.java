package com.cpilosenlaces.microservice.repository.disbeac;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cpilosenlaces.microservice.model.disbeac.Disbeac;

@Repository
public interface DisbeacRepository extends JpaRepository<Disbeac, UUID> {
    List<Disbeac> findByUser_id(UUID userId);

    List<Disbeac> findByMac(String mac);

    List<Disbeac> findAll();

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE disbeacs SET user_id = :userId WHERE id = :id", nativeQuery = true)
    void updateUserId(UUID userId, UUID id);
}
