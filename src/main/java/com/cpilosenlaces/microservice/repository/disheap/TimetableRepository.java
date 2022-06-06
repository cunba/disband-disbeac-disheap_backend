package com.cpilosenlaces.microservice.repository.disheap;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cpilosenlaces.microservice.model.disheap.Timetable;

@Repository
public interface TimetableRepository extends JpaRepository<Timetable, UUID> {
    List<Timetable> findByUserIdOrderByWeekDayDesc(UUID userId);

    List<Timetable> findByUserId(UUID userId);
}
