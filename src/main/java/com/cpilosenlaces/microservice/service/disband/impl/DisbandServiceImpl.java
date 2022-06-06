package com.cpilosenlaces.microservice.service.disband.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpilosenlaces.microservice.exception.NotFoundException;
import com.cpilosenlaces.microservice.model.disband.Disband;
import com.cpilosenlaces.microservice.repository.disband.DisbandRepository;
import com.cpilosenlaces.microservice.service.disband.DisbandService;

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
    public void updateUserId(UUID userId, UUID id) {
        dr.updateUserId(userId, id);;
    }

    @Override
    public void delete(Disband disband) {
        dr.delete(disband);
    }

    @Override
    public void deleteByUser(List<Disband> disbands) {
        dr.deleteAll(disbands);
    }

}
