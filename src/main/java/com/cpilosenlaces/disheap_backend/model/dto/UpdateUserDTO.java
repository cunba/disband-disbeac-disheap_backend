package com.cpilosenlaces.disheap_backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDTO {
    private String name = "string";
    private String surname = "string";
    private String birthday = "string";
    private String email = "string";
}
