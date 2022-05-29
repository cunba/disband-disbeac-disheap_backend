package com.cpilosenlaces.disheap_backend.model.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisbeacDTO {
    private String name;
    private String model;
    private String version;
    private float latitude;
    private float longitude;
    private UUID userId;
}
