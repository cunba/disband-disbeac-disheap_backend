package com.cpilosenlaces.microservice.model.disband.dto;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlarmDTO {
    @NotNull
    private long date;
    @NotNull
    private Boolean isRepetition;
    private String repetitionWeekDays;
    @NotNull
    private UUID disbandId;
}
