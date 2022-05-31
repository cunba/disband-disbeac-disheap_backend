package com.cpilosenlaces.disheap_backend.model.dto;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisbeacDTO {
    @NotNull
    private String mac;
    private String name;
    @NotNull
    private String model;
    @NotNull
    private String version;
    @NotNull
    private float latitude;
    @NotNull
    private float longitude;
    @NotNull
    private UUID userId;
}
