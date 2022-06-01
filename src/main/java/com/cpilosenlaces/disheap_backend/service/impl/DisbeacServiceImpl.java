package com.cpilosenlaces.disheap_backend.service.impl;

import java.util.List;
import java.util.UUID;

import com.cpilosenlaces.disheap_backend.exception.NotFoundException;
import com.cpilosenlaces.disheap_backend.model.Disbeac;
import com.cpilosenlaces.disheap_backend.repository.DisbeacRepository;
import com.cpilosenlaces.disheap_backend.service.DisbeacService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DisbeacServiceImpl implements DisbeacService {

    @Autowired
    private DisbeacRepository dr;

    @Override
    public List<Disbeac> findByUserId(UUID userId) {
        return dr.findByUserId(userId);
    }

    @Override
    public List<Disbeac> findByMac(String mac) {
        return dr.findByMac(mac);
    }

    @Override
    public Disbeac findById(UUID id) throws NotFoundException {
        return dr.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<Disbeac> findAll() {
        return dr.findAll();
    }

    @Override
    public Disbeac save(Disbeac disbeac) {
        return dr.save(disbeac);
    }

    @Override
    public void updateUserId(UUID userId, UUID id) {
        dr.updateUserId(userId, id);;
    }

    @Override
    public void delete(Disbeac disbeac) {
        dr.delete(disbeac);
    }

    @Override
    public void deleteByUser(List<Disbeac> disbeacs) {
        dr.deleteAll(disbeacs);
    }

}
