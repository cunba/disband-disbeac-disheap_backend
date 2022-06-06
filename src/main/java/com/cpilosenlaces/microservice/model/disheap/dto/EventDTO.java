package com.cpilosenlaces.microservice.model.disheap.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {
    private String name;
    private String notes;
    private long startDate;
    private long endDate;
    private String type;
    private UUID userId;
}
