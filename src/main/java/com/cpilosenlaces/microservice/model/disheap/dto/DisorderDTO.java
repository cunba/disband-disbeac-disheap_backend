package com.cpilosenlaces.microservice.model.disheap.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DisorderDTO {
    private String name;
    private String observations;
}
