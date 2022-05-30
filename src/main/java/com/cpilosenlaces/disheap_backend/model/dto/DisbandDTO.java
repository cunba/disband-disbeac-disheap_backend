package com.cpilosenlaces.disheap_backend.model.dto;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisbandDTO {
    private String name;
    @NotNull
    private String model;
    @NotNull
    private String firmware_version;
    @NotNull
    private UUID userId;
}
