package com.cpilosenlaces.microservice.service.disheap;

import java.util.List;
import java.util.UUID;

import com.cpilosenlaces.microservice.exception.NotFoundException;
import com.cpilosenlaces.microservice.model.disheap.Subject;

public interface SubjectService {
    List<Subject> findBySchoolYearId(UUID schoolYearId);

    Subject findById(UUID id) throws NotFoundException;

    Subject save(Subject subject);

    void delete(Subject subject);

    void deleteBySchoolYearId(List<Subject> subjects);
}
