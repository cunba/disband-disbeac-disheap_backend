package com.cpilosenlaces.microservice.service;

import java.util.List;
import java.util.UUID;

import com.cpilosenlaces.microservice.exception.NotFoundException;
import com.cpilosenlaces.microservice.model.UserModel;

public interface UserService {
    UserModel findById(UUID id) throws NotFoundException;

    List<UserModel> findByEmail(String email);

    List<UserModel> findAll();

    UserModel save(UserModel user);

    void updatePassword(UUID id, String password);

    void deleteUser(UserModel user);
}
