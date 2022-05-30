package com.cpilosenlaces.disheap_backend.model.dto;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeasureDTO {
    private float temperature = -1000;
    private float humidity = -1000;
    private float pressure = -1000;
    private float ambientNoise = -1000;
    private float lightning = -1000;
    private float redLightning = -1000;
    private float greenLightning = -1000;
    private float blueLightning = -1000;
    private float heartRate = -1000;
    private long date;
    @NotNull
    private UUID disbandId;
}
