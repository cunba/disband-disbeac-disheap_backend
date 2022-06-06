package com.cpilosenlaces.microservice.service.disheap.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpilosenlaces.microservice.exception.NotFoundException;
import com.cpilosenlaces.microservice.model.disheap.SchoolYear;
import com.cpilosenlaces.microservice.repository.disheap.SchoolYearRepository;
import com.cpilosenlaces.microservice.service.disheap.SchoolYearService;

@Service
public class SchoolYearServiceImpl implements SchoolYearService {

    @Autowired
    private SchoolYearRepository syr;

    @Override
    public List<SchoolYear> findAll() {
        return syr.findAll();
    }

    @Override
    public SchoolYear findById(UUID id) throws NotFoundException {
        return syr.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public SchoolYear save(SchoolYear schoolYear) {
        return syr.save(schoolYear);
    }

    @Override
    public void delete(SchoolYear schoolYear) {
        syr.delete(schoolYear);
    }

    @Override
    public void deleteAll() {
        syr.deleteAll();
    }

}
