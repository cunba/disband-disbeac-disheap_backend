package com.cpilosenlaces.microservice.repository.disheap;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cpilosenlaces.microservice.model.disheap.Homework;

@Repository
public interface HomeworkRepository extends JpaRepository<Homework, UUID> {
    List<Homework> findByDeadlineBetweenAndUserIdOrderByDeadlineDesc(long minDeadline, long maxDeadline, UUID userId);

    List<Homework> findByDeadlineBetweenAndSubjectIdAndUserIdOrderByDeadlineDesc(long minDeadline, long maxDeadline,
            UUID subjectId,
            UUID userId);

    List<Homework> findByUserId(UUID userId);
}
