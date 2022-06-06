package com.cpilosenlaces.microservice.model.disband.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeasureDTO {
    private float data;
    private long date;
    @NotNull
    private String disbandMac;
}
