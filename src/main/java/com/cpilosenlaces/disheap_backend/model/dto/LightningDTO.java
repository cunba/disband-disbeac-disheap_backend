package com.cpilosenlaces.disheap_backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LightningDTO {
    private float lightning;
    private float redLightning;
    private float greenLightning;
    private float blueLightning;
    private long date;
    private String disbandMac;
}
