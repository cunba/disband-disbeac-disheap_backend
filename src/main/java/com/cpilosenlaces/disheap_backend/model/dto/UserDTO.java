package com.cpilosenlaces.disheap_backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String name;
    private String surname;
    private String birthday;
    private Boolean isDisorder;
    private String email;
    private String password;
    private String role;
}
