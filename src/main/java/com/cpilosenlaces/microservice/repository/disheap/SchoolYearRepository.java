package com.cpilosenlaces.microservice.repository.disheap;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cpilosenlaces.microservice.model.disheap.SchoolYear;

@Repository
public interface SchoolYearRepository extends JpaRepository<SchoolYear, UUID> {

}
