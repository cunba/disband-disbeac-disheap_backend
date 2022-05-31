package com.cpilosenlaces.disheap_backend.service.impl;

import java.util.List;
import java.util.UUID;

import com.cpilosenlaces.disheap_backend.exception.NotFoundException;
import com.cpilosenlaces.disheap_backend.model.Disband;
import com.cpilosenlaces.disheap_backend.repository.DisbandRepository;
import com.cpilosenlaces.disheap_backend.service.DisbandService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DisbandServiceImpl implements DisbandService {

    @Autowired
    private DisbandRepository dr;

    @Override
    public List<Disband> findByUserId(UUID userId) {
        return dr.findByUserId(userId);
    }

    @Override
    public List<Disband> findByMac(String mac) {
        return dr.findByMac(mac);
    }

    @Override
    public Disband findById(UUID id) throws NotFoundException {
        return dr.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<Disband> findAll() {
        return dr.findAll();
    }

    @Override
    public Disband save(Disband disband) {
        return dr.save(disband);
    }

    @Override
    public void delete(Disband disband) {
        dr.delete(disband);
    }

    @Override
    public void deleteAll() {
        dr.deleteAll();
    }

}
