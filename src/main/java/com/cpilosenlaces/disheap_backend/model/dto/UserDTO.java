package com.cpilosenlaces.disheap_backend.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

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
    @NotNull
    @Email
    private String email;
    private String password;
    private String role;
}
