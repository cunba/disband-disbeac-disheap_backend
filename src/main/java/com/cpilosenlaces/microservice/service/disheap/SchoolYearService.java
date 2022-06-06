package com.cpilosenlaces.microservice.service.disheap;

import java.util.List;
import java.util.UUID;

import com.cpilosenlaces.microservice.exception.NotFoundException;
import com.cpilosenlaces.microservice.model.disheap.SchoolYear;

public interface SchoolYearService {
    List<SchoolYear> findAll();

    SchoolYear findById(UUID id) throws NotFoundException;

    SchoolYear save(SchoolYear schoolYear);

    void delete(SchoolYear schoolYear);

    void deleteAll();
}
