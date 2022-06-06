package com.cpilosenlaces.microservice.model.disbeac.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationDTO {
    private float latitude;
    private float longitude;
    private long date;
    private String disbeacMac;
}
