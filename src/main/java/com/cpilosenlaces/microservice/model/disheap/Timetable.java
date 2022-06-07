package com.cpilosenlaces.microservice.model.disheap;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "timetables")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Timetable {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false)
    private UUID id;
    @Column(name = "start_time")
    @JsonFormat(pattern = "hh:mm:ss")
    private String startTime;
    @Column(name = "end_time")
    @JsonFormat(pattern = "hh:mm:ss")
    private String endTime;
    @Column(name = "week_day")
    @PositiveOrZero
    @Min(value = 0)
    @Max(value = 4)
    private int weekDay;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference(value = "user-timetable")
    private UserModel user;
}
