package com.cpilosenlaces.microservice.model.disheap.dto;

import java.util.UUID;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimetableDTO {
    @JsonFormat(pattern = "hh:mm")
    private String startTime;
    @JsonFormat(pattern = "hh:mm")
    private String endTime;
    @PositiveOrZero
    @Min(value = 0)
    @Max(value = 4)
    private int weekDay;
    private UUID subjectId;
    private UUID userId;
}
