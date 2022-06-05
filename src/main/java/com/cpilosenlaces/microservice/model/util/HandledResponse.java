package com.cpilosenlaces.microservice.model.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HandledResponse {
    private String message;
    private int number;
}
