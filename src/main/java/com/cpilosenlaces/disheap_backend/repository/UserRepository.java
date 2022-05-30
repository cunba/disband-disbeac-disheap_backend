package com.cpilosenlaces.disheap_backend.repository;

import java.util.List;
import java.util.UUID;

import com.cpilosenlaces.disheap_backend.model.UserModel;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends CrudRepository<UserModel, UUID> {
    List<UserModel> findByEmail(String email);

    List<UserModel> findAll();

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE users SET password = :password WHERE id = :id", nativeQuery = true)
    void updatePassword(String password, UUID id);
}
