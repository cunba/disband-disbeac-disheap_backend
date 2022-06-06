package com.cpilosenlaces.microservice.model.disheap.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectDTO {
    private String name;
    private UUID schoolYearId;
}
