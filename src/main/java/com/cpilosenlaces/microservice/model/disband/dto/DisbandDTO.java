package com.cpilosenlaces.microservice.model.disband.dto;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisbandDTO {
    @NotNull
    private String mac;
    @NotNull
    private String model;
    @NotNull
    private String version;
    private long date;
    @NotNull
    private UUID userId;
}
