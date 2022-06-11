package com.cpilosenlaces.microservice.model.disband;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeasureResponse<T> {
    private List<T> measures;
    private float minMeasure;
    private float maxMeasure;
}
