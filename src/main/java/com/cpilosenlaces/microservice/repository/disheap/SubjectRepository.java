package com.cpilosenlaces.microservice.repository.disheap;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cpilosenlaces.microservice.model.disheap.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, UUID> {
    List<Subject> findBySchoolYearId(UUID schoolYearId);
}
