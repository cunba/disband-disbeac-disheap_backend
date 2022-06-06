package com.cpilosenlaces.microservice.repository.disheap;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cpilosenlaces.microservice.model.disheap.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {
    List<UserModel> findByEmail(String email);

    List<UserModel> findAll();

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE users SET password = :password, updated = :updated WHERE id = :id", nativeQuery = true)
    void updatePassword(String password, long updated, UUID id);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE users SET email = :email, updated = :updated WHERE id = :id", nativeQuery = true)
    void updateEmail(String email, long updated, UUID id);
}
