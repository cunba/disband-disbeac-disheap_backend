package com.cpilosenlaces.disheap_backend.service.impl;

import java.util.List;
import java.util.UUID;

import com.cpilosenlaces.disheap_backend.exception.NotFoundException;
import com.cpilosenlaces.disheap_backend.model.UserModel;
import com.cpilosenlaces.disheap_backend.repository.UserRepository;
import com.cpilosenlaces.disheap_backend.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
