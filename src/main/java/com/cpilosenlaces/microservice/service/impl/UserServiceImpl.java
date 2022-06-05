package com.cpilosenlaces.microservice.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpilosenlaces.microservice.exception.NotFoundException;
import com.cpilosenlaces.microservice.model.UserModel;
import com.cpilosenlaces.microservice.repository.UserRepository;
import com.cpilosenlaces.microservice.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository ur;

    @Override
    public UserModel findById(UUID id) throws NotFoundException {
        return ur.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<UserModel> findByEmail(String email) {
        return ur.findByEmail(email);
    }

    @Override
    public List<UserModel> findAll() {
        return ur.findAll();
    }

    @Override
    public UserModel save(UserModel user) {
        return ur.save(user);
    }

    @Override
    public void updatePassword(UUID id, String password) {
        ur.updatePassword(password, id);
    }

    @Override
    public void deleteUser(UserModel user) {
        ur.delete(user);
    }

}
