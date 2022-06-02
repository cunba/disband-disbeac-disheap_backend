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
    private float data;
    private long date;
    @NotNull
    private UUID disbandId;
}
