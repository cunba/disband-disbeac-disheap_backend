package com.cpilosenlaces.disheap_backend.model.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlarmDTO {
    private long date;
    private Boolean isRepetition;
    private String repetitionWeekDays;
    private UUID disbandId;
}
