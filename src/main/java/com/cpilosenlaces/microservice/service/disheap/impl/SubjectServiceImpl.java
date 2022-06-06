package com.cpilosenlaces.microservice.service.disheap.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpilosenlaces.microservice.exception.NotFoundException;
import com.cpilosenlaces.microservice.model.disheap.Subject;
import com.cpilosenlaces.microservice.repository.disheap.SubjectRepository;
import com.cpilosenlaces.microservice.service.disheap.SubjectService;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectRepository sr;

    @Override
    public List<Subject> findBySchoolYearId(UUID schoolYearId) {
        return sr.findBySchoolYearId(schoolYearId);
    }

    @Override
    public Subject findById(UUID id) throws NotFoundException {
        return sr.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public Subject save(Subject subject) {
        return sr.save(subject);
    }

    @Override
    public void delete(Subject subject) {
        sr.delete(subject);
    }

    @Override
    public void deleteBySchoolYearId(List<Subject> subjects) {
        sr.deleteAll(subjects);
    }

}
