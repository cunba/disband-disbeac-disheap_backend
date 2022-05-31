package com.cpilosenlaces.disheap_backend.model.util;

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
