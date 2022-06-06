package com.cpilosenlaces.microservice.service.disheap.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpilosenlaces.microservice.exception.NotFoundException;
import com.cpilosenlaces.microservice.model.disheap.Homework;
import com.cpilosenlaces.microservice.repository.disheap.HomeworkRepository;
import com.cpilosenlaces.microservice.service.disheap.HomeworkService;

@Service
public class HomeworkServiceImpl implements HomeworkService {

    @Autowired
    private HomeworkRepository hr;

    @Override
    public List<Homework> findByDeadlineBetweenAndUserId(long minDeadline, long maxDeadline, UUID userId) {
        return hr.findByDeadlineBetweenAndUserIdOrderByDeadlineDesc(minDeadline, maxDeadline, userId);
    }

    @Override
    public List<Homework> findByDeadlineBetweenAndSubjectIdAndUserId(long minDeadline, long maxDeadline, UUID subjectId,
            UUID userId) {

        return hr.findByDeadlineBetweenAndSubjectIdAndUserIdOrderByDeadlineDesc(minDeadline, maxDeadline, subjectId,
                userId);
    }
    
    @Override
    public List<Homework> findByUserId(UUID userId) {
        return hr.findByUserId(userId);
    }

    @Override
    public Homework findById(UUID id) throws NotFoundException {
        return hr.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public Homework save(Homework homework) {
        return hr.save(homework);
    }

    @Override
    public void delete(Homework homework) {
        hr.delete(homework);
    }

    @Override
    public void deleteByUserId(List<Homework> homeworks) {
        hr.deleteAll(homeworks);
    }

}
