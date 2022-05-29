package com.cpilosenlaces.disheap_backend.model.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeasureDTO {
    private float temperature;
    private float humidity;
    private float pressure;
    private float ambient_noise;
    private float lightning;
    private float redLightning;
    private float greenLightning;
    private float blueLightning;
    private float heartRate;
    private long date;
    private UUID disbandId;
}
