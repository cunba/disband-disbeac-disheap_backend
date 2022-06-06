package com.cpilosenlaces.microservice.service.disheap.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpilosenlaces.microservice.exception.NotFoundException;
import com.cpilosenlaces.microservice.model.disheap.Disorder;
import com.cpilosenlaces.microservice.repository.disheap.DisorderRepository;
import com.cpilosenlaces.microservice.service.disheap.DisorderService;

@Service
public class DisorderServiceImpl implements DisorderService {

    @Autowired
    private DisorderRepository dr;

    @Override
    public List<Disorder> findAll() {
        return dr.findAll();
    }

    @Override
    public Disorder findById(UUID id) throws NotFoundException {
        return dr.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public Disorder save(Disorder disorder) {
        return dr.save(disorder);
    }

    @Override
    public void delete(Disorder disorder) {
        dr.delete(disorder);
    }

    @Override
    public void deleteAll() {
        dr.deleteAll();
    }

}
