package com.cpilosenlaces.disheap_backend.model.dto;

import java.util.UUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeasureDTO {
    @Null
    private float temperature;
    @Null
    private float humidity;
    @Null
    private float pressure;
    @Null
    private float ambient_noise;
    @Null
    private float lightning;
    @Null
    private float redLightning;
    @Null
    private float greenLightning;
    @Null
    private float blueLightning;
    @Null
    private float heartRate;
    @Null
    private long date;
    @NotNull
    private UUID disbandId;
}
