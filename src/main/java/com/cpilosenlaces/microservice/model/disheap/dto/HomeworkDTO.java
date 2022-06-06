package com.cpilosenlaces.microservice.model.disheap.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomeworkDTO {
    private String name;
    private String description;
    private long deadline;
    private UUID subjectId;
    private UUID userId;
}
