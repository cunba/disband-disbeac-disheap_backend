package com.cpilosenlaces.disheap_backend.repository;

import java.util.List;
import java.util.UUID;

import com.cpilosenlaces.disheap_backend.model.UserModel;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserModel, UUID> {
    List<UserModel> findByEmail(String email);

    List<UserModel> findAll();
}
