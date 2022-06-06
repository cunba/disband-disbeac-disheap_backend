package com.cpilosenlaces.microservice.service.disheap;

import java.util.List;
import java.util.UUID;

import com.cpilosenlaces.microservice.exception.NotFoundException;
import com.cpilosenlaces.microservice.model.disheap.UserModel;

public interface UserService {
    UserModel findById(UUID id) throws NotFoundException;

    List<UserModel> findByEmail(String email);

    List<UserModel> findAll();

    UserModel save(UserModel user);

    void updatePassword(UUID id, String password);

    void updateEmail(UUID id, String email);

    void deleteUser(UserModel user);
}
