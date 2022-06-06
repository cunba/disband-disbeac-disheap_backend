package com.cpilosenlaces.microservice.service.disheap;

import java.util.List;
import java.util.UUID;

import com.cpilosenlaces.microservice.exception.NotFoundException;
import com.cpilosenlaces.microservice.model.disheap.Homework;

public interface HomeworkService {
    List<Homework> findByDeadlineBetweenAndUserId(long minDeadline, long maxDeadline, UUID userId);

    List<Homework> findByDeadlineBetweenAndSubjectIdAndUserId(long minDeadline, long maxDeadline, UUID subjectId,
            UUID userId);

    List<Homework> findByUserId(UUID userId);

    Homework findById(UUID id) throws NotFoundException;

    Homework save(Homework homework);

    void delete(Homework homework);

    void deleteByUserId(List<Homework> homeworks);
}
