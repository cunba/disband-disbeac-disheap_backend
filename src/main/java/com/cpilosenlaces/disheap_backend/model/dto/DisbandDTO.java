package com.cpilosenlaces.disheap_backend.model.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisbandDTO {
    private String name;
    private String model;
    private String firmware_version;
    private UUID userId;
}
