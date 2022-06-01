package com.cpilosenlaces.disheap_backend.model.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationDisbeacDTO {
    private String email;
    private String password;
    private float latitude;
    private float longitude;
    private long date;
    private UUID disbeacId;
}
