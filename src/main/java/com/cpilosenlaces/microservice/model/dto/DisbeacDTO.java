package com.cpilosenlaces.microservice.model.dto;

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
    @NotNull
    private String model;
    @NotNull
    private String version;
    private String date;
    @NotNull
    private UUID userId;
}
